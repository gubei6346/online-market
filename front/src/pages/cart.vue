<template>
  <div class="cart">
    <order-header title="我的购物车">
      <template v-slot:tip>
        <span>温馨提示：产品是否购买成功，以最终下单为准哦，请尽快结算</span>
      </template>
    </order-header>
    <div class="wrapper">
      <div class="container">
        <div class="cart-box">
          <ul class="cart-item-head">
            <li class="col-1"><span class="checkbox" v-bind:class="{'checked':allChecked}" @click="toggleAll"></span>全选</li>
            <li class="col-3">商品名称</li>
            <li class="col-1">单价</li>
            <li class="col-2">数量</li>
            <li class="col-1">小计</li>
            <li class="col-1">操作</li>
          </ul>
          <ul class="cart-item-list">
            <li class="cart-item" v-for="(item,index) in list" v-bind:key="index">
              <div class="item-check">
                <span class="checkbox"     v-bind:class="{'checked':item.productSelected}"      @click="updateCart(item)"></span>
              </div>
              <div class="item-name">
                <img v-lazy="item.productPic" alt="">
                <span>{{item.productName + ' , ' + item.productSubTitle}}</span>
              </div>
              <div class="item-price">{{item.price}}</div>
              <div class="item-num">
                <div class="num-box">
                  <a href="javascript:;" @click="updateCart(item,'-')">-</a>
                  <span >{{item.quantity}}</span>
                  <a href="javascript:;"  @click="updateCart(item,'+')">+</a>
                </div>
                <div>当前库存：{{item.stock}}</div>
              </div>
              <div class="item-total">{{item.quantity*item.price}}</div>
              <div class="item-del" @click="delProduct(item)"></div>
            </li>
          </ul>
        </div>
        <div class="order-wrap clearfix">
          <div class="cart-tip fl">
            <a href="/#/index">继续购物</a>
            共<span>{{list.length}}</span>件商品，已选择<span>{{checkedNum}}</span>件，总价<span>{{calcSum}}</span>
          </div>
          <div class="total fr">
             <a href="javascript:;" class="btn" @click="order">去结算</a>
          </div>
        </div>
      </div>
    </div>
    <service-bar></service-bar>
    <nav-footer></nav-footer>
  </div>
</template>
<script>
  import OrderHeader from './../components/OrderHeader'
  import ServiceBar from './../components/ServiceBar'
  import NavFooter from './../components/NavFooter'
  import Qs from 'qs'
  import constStore from './../store/constStore.js'
  export default{
    name:'index',
    components:{
      OrderHeader,
      ServiceBar,
      NavFooter
    },
    data(){
      return {
        list:[],//商品列表
        allChecked:false,//是否全选
        cartTotalPrice:0,//商品总金额
        checkedNum:0//选中商品数量
      }
    }, 
    computed:{
      calcSum(){
        let sum=0;
            this.list.map((item)=>{
              // 是否选中
               if(item.productSelected){
                  sum+=item.price*item.quantity
                }
             });
        return sum;
      }
    },
    mounted(){
      this.getCartList();
    },
    methods:{
      // 获取购物车列表
      getCartList(){
        this.axios.get('/car/list').then((res)=>{
          this.renderData(res);
        })
      },
      // 更新购物车数量和购物车单选状态
      updateCart(item,type){
        let quantity = item.quantity;
            //selected = item.productSelected;
        if(type == '-'){
          if(quantity == 1){
            this.$message.warning('商品至少保留一件');
            return;
          }
           --quantity;
            item.quantity=quantity;
          this.axios.post('/car/update/quantity',Qs.stringify({
            id:item.id,
            quantity:item.quantity
          }),{headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then(()=>{
           });
        }else if(type == '+'){
          // 如果当前数量等于库存就不能再加
          if(quantity>=item.stock){
            this.$message.warning('库存不足！');
            return;
          }

            ++quantity; 
            item.quantity=quantity;
         
          this.axios.post('/car/update/quantity',Qs.stringify({
            id:item.id,
            quantity:item.quantity
          }),{headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then(()=>{
           });


        }else{
        // 选择
        if(quantity>item.stock){
            this.$message.warning('库存不足！');
            return;
          }

         var index=0;
         this.list.map((oitem)=>{
               if(oitem.id==item.id){
                   oitem.productSelected = !oitem.productSelected;

                   if(oitem.productSelected){
                     this.checkedNum++; 
                   }else{
                     this.checkedNum--;
                   }
                     this.$set(this.list,index,oitem);
               }
          index++;
         });
        }
      },
       // 删除购物车商品
      delProduct(item){
          this.axios.post('/car/delete',Qs.stringify({
            ids:item.id
          }),{headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then(()=>{
            this.$message.success('删除成功');
            var index=0;
            var target=0;
            this.list.map((oitem)=>{
                if(oitem.id==item.id){
                   target=index;  
                }
                 index++;
            });
            this.checkedNum--;
            this.list.splice(target,1);

          });


      },
      // 控制全选功能
      toggleAll(){
           this.list.map((item)=>{
             // 为每个项设置productSelected 
             if(item.quantity>item.stock){
              this.$message.warning('库存不足！');
              return;
             }
               item.productSelected=!this.allChecked;
           });
         this.allChecked=!this.allChecked;
         if(this.allChecked){
           this.checkedNum=this.list.length;
         }else{
           this.checkedNum=0;
         }
      },
      // 公共赋值
      renderData(res){
        this.list =  res; 
        
        this.list.map((item)=>{
          if(item.productSelected==undefined){
              item.productSelected=false;
          }
        }); 

        //this.list.filter(item=>item.productSelected).length;
      },
      // 购物车下单
      order(){
          let isCheck = this.list.every(item=>!item.productSelected);
           
       if(isCheck){
          this.$message.warning('请选择一件商品');
        }else{
            constStore.itemids=[];
            this.list.map((item)=>{
              // 如果选中
               if(item.productSelected){
                 // 当前购物车id
                 constStore.itemids.push(item.id);
                }
             });
             // 替换路由：不会刷新页面 会保留根vue对象中的值 ， 跳转页面： 重新加载会创建创建vue对象
             this.$router.push('/order/confirm');
        }
      }
    },
  }
</script>
<style lang="scss">
  .cart{
    .wrapper{
      background-color:#F5F5F5;
      padding-top:30px;
      padding-bottom:220px;
      .cart-box{
        background-color:#fff;
        font-size:14px;
        color:#999999;
        text-align:center;
        .checkbox{
          display: inline-block;
          width: 22px;
          height: 22px;
          border: 1px solid #E5E5E5;
          vertical-align: middle;
          margin-right: 17px;
          cursor:pointer;
          &.checked{
            background:url('/imgs/icon-gou.png') #FF6600 no-repeat center;
            background-size:16px 12px;
            border:none;
          }
        }
        .cart-item-head{
          display:flex;
          height: 79px;
          line-height: 79px;
          .col-1{
            flex:1;
          }
          .col-2{
            flex:2;
          }
          .col-3{
            flex:3;
          }
        }
        .cart-item-list{
          .cart-item{
            display:flex;
            align-items:center;
            height:125px;
            border-top:1px solid #E5E5E5;
            font-size:16px;
            .item-check{
              flex:1;
            }
            .item-name{
              flex:3;
              font-size: 18px;
              color: #333333;
              display: flex;
              align-items: center;
              img{
                width:80px;
                height:80px;
                vertical-align:middle;
              }
              span{
                margin-left: 30px;
              }
            }
            .item-price{
              flex:1;
              color:#333333;
            }
            .item-num{
              flex:2;
              .num-box{
                display:inline-block;
                width:150px;
                height:40px;
                line-height:40px;
                border:1px solid #E5E5E5;
                font-size:14px;
                a{
                  display:inline-block;
                  width:50px;
                  color:#333333;
                }
                span{
                  display:inline-block;
                  width:50px;
                  color:#333333;
                }
              }
            }
            .item-total{
              flex:1;
              color:#FF6600;
            }
            .item-del{
              flex:1;
              width:14px;
              height:12px;
              background:url('/imgs/icon-close.png') no-repeat center;
              background-size:contain;
              cursor:pointer;
            }
          }
        }
      }
      .order-wrap{
        font-size:14px;
        color: #666666;
        margin-top: 20px;
        height: 50px;
        line-height: 50px;
        .cart-tip{
          margin-left: 29px;
          a{
            color: #666666;
            margin-right:37px;
          }
          span{
            color:#FF6600;
            margin:0 5px;
          }
        }
        .total{
          font-size:14px;
          color:#FF6600;
          span{
            font-size:24px;
          }
          a{
            width:202px;
            height:50px;
            line-height:50px;
            font-size:18px;
            margin-left:37px;
          }
        }
      }
    }
  }
</style>