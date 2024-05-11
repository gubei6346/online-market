package com.tulingxueyuan.mall.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员与产品分类关系表（用户喜欢的分类）
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_product_category_relation")
@ApiModel(value="UmsMemberProductCategoryRelation对象", description="会员与产品分类关系表（用户喜欢的分类）")
public class UmsMemberProductCategoryRelation implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long memberId;

    private Long productCategoryId;


}
