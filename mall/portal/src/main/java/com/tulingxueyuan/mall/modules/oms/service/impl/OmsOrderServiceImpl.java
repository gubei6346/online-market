package com.tulingxueyuan.mall.modules.oms.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mall.common.service.RedisService;
import com.tulingxueyuan.mall.dto.*;
import com.tulingxueyuan.mall.modules.oms.mapper.OmsCartItemMapper;
import com.tulingxueyuan.mall.modules.oms.model.OmsCartItem;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mall.modules.oms.mapper.OmsOrderMapper;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderItem;
import com.tulingxueyuan.mall.modules.oms.service.OmsCartItemService;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderItemService;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderSettingService;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import com.tulingxueyuan.mall.modules.pms.service.PmsSkuStockService;
import com.tulingxueyuan.mall.modules.ums.model.UmsMember;
import com.tulingxueyuan.mall.modules.ums.model.UmsMemberReceiveAddress;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberReceiveAddressService;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {
    @Autowired
    OmsCartItemService cartItemService;
    @Autowired
    PmsProductService productService;
    @Autowired
    UmsMemberReceiveAddressService addressService;
    @Autowired
    UmsMemberService memberService;
    @Autowired
    OmsCartItemMapper cartItemMapper;
//    @Autowired
//    RedisService redisService;
//    @Value("${redis.key.prefix.orderId}")
//    private String REDIS_KEY_PREFIX_ORDER_ID;
    @Autowired
    OmsOrderItemService orderItemService;
    @Autowired
    PmsSkuStockService skuStockService;
    @Autowired
    OmsOrderSettingService orderSettingService;

    @Override
    public ConfirmOrderDTo generateConfirmOrder(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
        ConfirmOrderDTo confirmOrderDTo = new ConfirmOrderDTo();
        //商品
        List<OmsCartItem> omsCartItems = cartItemService.listByIds(ids);
        confirmOrderDTo.setCartList(omsCartItems);
        //价格
        calcCatAmount(confirmOrderDTo);
        //地址
        //UmsMember currentMember = memberService.getCurrentMember();
        QueryWrapper<UmsMemberReceiveAddress> queryWrapper = new QueryWrapper<>();
        //queryWrapper.lambda().eq(UmsMemberReceiveAddress::getMemberId,currentMember);
        List<UmsMemberReceiveAddress> list = addressService.list(queryWrapper);
        confirmOrderDTo.setAddressList(list);
        return  confirmOrderDTo;

    }

    @Override
    @Transactional
    public OmsOrder generateOrder(OrderParamDTO paramDTO) {
        QueryWrapper<OmsCartItem> queryWrapper = new QueryWrapper<>();
        List<CartItemStockDTO> cartItemStockByIds = cartItemMapper.getCartItemStockByIds(queryWrapper);

        //保存订单主表oms_order信息  订单号
        OmsOrder omsOrder = newOrder(paramDTO, cartItemStockByIds);
        this.save(omsOrder);
        // 保存订单详情表oms_order_item( 购物车转移）
        List<OmsOrderItem> list=newOrderItems(omsOrder,cartItemStockByIds);
        orderItemService.saveBatch(list);
        return omsOrder;
    }

    public void calcCatAmount(ConfirmOrderDTo confirmOrderDTo){
        //计算商品数量
        Integer productTotal=0;
        //总价
        BigDecimal priceTotal=new BigDecimal(0);
        //运费
        BigDecimal freightAmount=new BigDecimal(0);
        //应付价格
        BigDecimal payAmount;
        for (OmsCartItem omsCartItem : confirmOrderDTo.getCartList()) {
           //商品总件数
            productTotal+= omsCartItem.getQuantity();
            //总价
            priceTotal= priceTotal.add(omsCartItem.getPrice().multiply(new BigDecimal(omsCartItem.getQuantity()))) ;

            PmsProduct product = productService.getById(omsCartItem.getProductId());
            String serviceIds = product.getServiceIds();
            String[] serviceIdsArray = serviceIds.split(",");
            if (serviceIdsArray.length>0){
                //判断是否包邮
               if(! ArrayUtil.containsAny(serviceIdsArray, 3)){
                  freightAmount= freightAmount.add(new BigDecimal(10));
               }
            }

        }
        confirmOrderDTo.setProductTotal(productTotal);
        confirmOrderDTo.setPriceTotal(priceTotal);
        confirmOrderDTo.setFreightAmount(freightAmount);
        confirmOrderDTo.setPayAmount(priceTotal.subtract(freightAmount));
    }

    public OmsOrder newOrder(OrderParamDTO paramDTO,  List<CartItemStockDTO> cartItemStockByIds){
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setCreateTime(new Date());
        omsOrder.setModifyTime(new Date());


        //  计算价格 需要传入ConfirmOrderDTO
        ConfirmOrderDTo confirmOrderDTO = new ConfirmOrderDTo();
        // 1.购物车集合 因为计算价格是根据购物车列表信息来计算的
        List<OmsCartItem> omsCartItemsList = new ArrayList<>();
        // 循环将CartItemStockDTO 转换为OmsCartItem
        for (CartItemStockDTO cartItem : cartItemStockByIds) {
            omsCartItemsList.add(cartItem);
        }

        confirmOrderDTO.setCartList(omsCartItemsList);
        // 2、计算价格
        calcCatAmount(confirmOrderDTO);

        // 商品总价
        omsOrder.setTotalAmount(confirmOrderDTO.getPriceTotal());
        // 应付总金额
        omsOrder.setPayAmount(confirmOrderDTO.getPayAmount());
        // 运费金额
        omsOrder.setFreightAmount(confirmOrderDTO.getFreightAmount());
        /*
        促销金额待升级
         */
        // 订单来源：0->PC订单；1->app订单
        omsOrder.setSourceType(1);
        // 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        omsOrder.setStatus(0);
        omsOrder.setOrderType(0);   //订单类型：0->正常订单；1->秒杀订单
        omsOrder.setAutoConfirmDay(15);  // 自动确认收货时间

        // 地址
        QueryWrapper<UmsMemberReceiveAddress> addressQueryWrapper = new QueryWrapper<UmsMemberReceiveAddress>();

        UmsMemberReceiveAddress address = addressService.getOne(addressQueryWrapper);
        //收货人姓名
        omsOrder.setReceiverName(address.getName());
        // receiver_phone` varchar(32) NOT NULL COMMENT '收货人电话',
        omsOrder.setReceiverPhone(address.getPhoneNumber());
        //`receiver_post_code` varchar(32) DEFAULT NULL COMMENT '收货人邮编',
        omsOrder.setReceiverPostCode(address.getPostCode());
        //receiver_province` varchar(32) DEFAULT NULL COMMENT '省份/直辖市',
        omsOrder.setReceiverProvince(address.getProvince());
        //城市,
        omsOrder.setReceiverCity(address.getCity());
        // '区'
        omsOrder.setReceiverRegion(address.getRegion());
        //'详细地址'
        omsOrder.setReceiverDetailAddress(address.getDetailAddress());
        // '确认收货状态：0->未确认；1->已确认'
        omsOrder.setConfirmStatus(0);
        // 生产订单编码
        omsOrder.setOrderSn(getOrderSn(omsOrder));
        return omsOrder;
    }

    private List<OmsOrderItem> newOrderItems(OmsOrder omsOrder,List<CartItemStockDTO> cartItemStockByIds) {

        List<OmsOrderItem> list=new ArrayList<>();
        for (CartItemStockDTO cartItemStockById : cartItemStockByIds) {
            OmsOrderItem orderItem=new OmsOrderItem();
            orderItem.setOrderId(omsOrder.getId());
            orderItem.setOrderSn(omsOrder.getOrderSn());
            orderItem.setProductId(cartItemStockById.getProductId());
            orderItem.setProductPic(cartItemStockById.getProductPic());
            orderItem.setProductName(cartItemStockById.getProductName());
            orderItem.setProductBrand(cartItemStockById.getProductBrand());
            orderItem.setProductSn(cartItemStockById.getProductSn());
            orderItem.setProductPrice(cartItemStockById.getPrice());
            orderItem.setProductQuantity(cartItemStockById.getQuantity());
            orderItem.setProductSkuId(cartItemStockById.getProductSkuId());
            orderItem.setProductSkuCode(cartItemStockById.getProductSkuCode());
            orderItem.setProductCategoryId(cartItemStockById.getProductCategoryId());
            orderItem.setSp1(cartItemStockById.getSp1());
            orderItem.setSp2(cartItemStockById.getSp2());
            orderItem.setSp3(cartItemStockById.getSp3());
            list.add(orderItem);
        }
        return list;
    }
    public String getOrderSn(OmsOrder omsOrder){
        // 订单编号
        StringBuilder sb=new StringBuilder();
        //8位日期
        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date());
        sb.append(yyyyMMdd);
        //2位平台号码  1.pc  2.app
        //String.format：参数说
        // 0 代表前面补充零
        // 6 代表补充长度
        // d 代表正数
        String sourceTyp = String.format("%02d", omsOrder.getSourceType());
        sb.append(sourceTyp);
        // 6位以上自增id
        // redis incr  原子性
        // 适合并发的自增方式：
        // 拿到当前的6位以上自增 如果小于6位，自动补全0
        sb.append("12345678");
        return sb.toString();

    }

}
