<template>
  <div class="header">
    <div class="nav-topbar">
      <div class="container">
        <div class="topbar-menu">
          <a href="javascript:;">家具商城</a> 
        </div>
        <div class="topbar-user">
          <a href="/#/order/list" v-if="username">我的订单</a>
          <a href="javascript:;" class="my-cart" @click="goToCart"><span class="icon-cart"></span>购物车</a>
        </div>
      </div>
    </div>
    <div class="nav-header">
      <div class="container">
        <div class="header-logo">
          <div>
          <img href="../assets/image/home.png">
          </div>
        </div>
        <div class="header-search">
          <div class="wrapper">
            <input type="text"  v-model="keyword" name="keyword">
            <a href="javascript:;" @click="doSearch()"></a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'

  export default{
    name:'nav-header',
    data(){
      return {
        phoneList: [
         {id:1,mainImage:"/imgs/nav-img/nav-3-1.jpg",subtitle:"产品名称",name:"产品名称",price:1000,currency:'元'},
         {id:2,mainImage:"/imgs/nav-img/nav-3-2.jpg",subtitle:"产品名称",name:"产品名称",price:1000,currency:'元'},
         {id:3,mainImage:"/imgs/nav-img/nav-3-3.png",subtitle:"产品名称",name:"产品名称",price:1000,currency:'元'},
         {id:4,mainImage:"/imgs/nav-img/nav-3-4.jpg",subtitle:"产品名称",name:"产品名称",price:1000,currency:'元'},
         {id:5,mainImage:"/imgs/nav-img/nav-3-5.jpg",subtitle:"产品名称",name:"产品名称",price:1000,currency:'元'},
         {id:6,mainImage:"/imgs/nav-img/nav-3-6.jpg",subtitle:"产品名称",name:"产品名称",price:1000,currency:'元'},

        ],
        keyword:"请输入产品关键字"
      }
    },
    computed:{
       username(){
        return this.$store.state.username;
      },
      cartCount(){ 
        // 取
        return this.$store.state.cartCount;
      }, 
      ...mapState(['username','cartCount'])
    },
    filters:{
      currency(val){
        if(!val)return '0.00';
        return '￥' + val.toFixed(2) + '元';
      }
    },
    mounted(){
      this.getProductList();
      let params = this.$route.params;
      if(params && params.from == 'login'){
        this.getCartCount();
      }
    },
    methods:{
      login(){
        this.$router.push('/login');
      },
      getProductList(){
      },
      getCartCount(){
      },
      logout(){
          this.$cookie.set('token','');
          this.$cookie.set('username','');
          this.$message.success('退出成功');
          this.$store.dispatch('saveUserName','');
          this.$store.dispatch('saveCartCount','0');

      },
      doSearch(){
               
            
           this.$router.push(`/searchResult/${this.keyword}`).catch(err => { window.console.log(err)});
      },
      goToCart(){
        this.$router.push('/cart');
      }
    }
  }
</script>
<style lang="scss">
  @import './../assets/scss/base.scss';
  @import './../assets/scss/mixin.scss';
  @import './../assets/scss/config.scss';
  .header{
    .nav-topbar{
      height:39px;
      line-height:39px;
      background-color:#333333;
      color:#B0B0B0;
      .container{
        @include flex();
        a{
          display:inline-block;
          color:#B0B0B0;
          margin-right:17px;
        }
        .my-cart{
          width:110px;
          background-color:#FF6600;
          text-align:center;
          color:#ffffff;
          margin-right:0;
          .icon-cart{
            @include bgImg(16px,12px,'/imgs/icon-cart-checked.png');
            margin-right:4px;
          }
        }
      }
    }
    .nav-header{
      .container{
        position:relative;
        height:112px;
        @include flex();
        .header-menu{
          display:inline-block;
          width:643px;
          padding-left:209px;
          .item-menu{
            display:inline-block;
            color:#333333;
            font-weight:bold;
            font-size:16px;
            line-height:112px;
            margin-right:20px;
            span{
              cursor:pointer;
            }
            &:hover{
              color:$colorA;
              .children{
                height:220px;
                opacity:1;
              }
            }
            .children{
              position:absolute;
              top:112px;
              left:0;
              width:1226px;
              height:0;
              opacity:0;
              overflow:hidden;
              border-top:1px solid #E5E5E5;
              box-shadow:0px 7px 6px 0px rgba(0, 0, 0, 0.11);
              z-index: 10;
              transition:all .5s;
              background-color: #ffffff;
              .product{
                position:relative;
                float:left;
                width:16.6%;
                height:220px;
                font-size:12px;
                line-height:12px;
                text-align: center;
                a{
                  display:inline-block;
                }
                img{
                  width:auto;
                  height:111px;
                  margin-top:26px;
                }
                .pro-img{
                  height:137px;
                }
                .pro-name{
                  font-weight:bold;
                  margin-top:19px;
                  margin-bottom:8px;
                  color:$colorB;
                }
                .pro-price{
                  color:$colorA;
                }
                &:before{
                  content:' ';
                  position:absolute;
                  top:28px;
                  right:0;
                  border-left:1px solid $colorF;
                  height:100px;
                  width:1px;
                }
                &:last-child:before{
                  display:none;
                }
              }
            }
          }
        }
        .header-search{
          width:319px;
          .wrapper{
            height:50px;
            border:1px solid #E0E0E0;
            display:flex;
            align-items:center;
            input{
              border:none;
              box-sizing: border-box;
              border-right:1px solid #E0E0E0;
              width:264px;
              height:50px;
              padding-left:14px;
            }
            a{
              @include bgImg(18px,18px,'/imgs/icon-search.png');
              margin-left:17px;
            }
          }
        }
      }
    }
  }
</style>