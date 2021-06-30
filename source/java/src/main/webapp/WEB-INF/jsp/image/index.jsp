<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0">
  <!-- import CSS -->
  <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
  <link rel="stylesheet" href="../css/base.css">
  <title>图片管理</title>
</head>
<body>
  <div id="app">
    <el-container class="main">
      <el-aside :width="tabWidth+'px'">
        <div>
          <div class="isClossTab">图片管理</div>

          <el-menu router :class="'menu'" 
                 :default-active="$route.path"
                 :default-openeds=["1"]
                 class="el-menu-vertical-demo"
                 @select="handleSelect"
                 @open="handleOpen"
                 @close="handleClose"
                 :collapse="false"
                 background-color="#545c64"
                 text-color="#fff"
                 active-text-color="#ffd04b">
            <el-submenu index="1">
              <span slot="title">影集</span>
              <el-menu-item :index="'/albums/'+item" v-for="item in timeflow" :key="'/albums/'+item">
                <i class="el-icon-folder"></i>
                <span slot="title">{{item}}</span>
              </el-menu-item>
            </el-submenu>
            <!-- <el-menu-item :index="item.path" v-for="item in menuInfo" :key="item.path">
              <i :class="item.icon"></i>
              <span slot="title">{{item.label}}</span>
            </el-menu-item>  -->
          </el-menu>

        </div>
      </el-aside>
      <el-container>
        <el-header class="main-header">
          <el-dropdown>
            <span class="el-dropdown-link">
              <img :src="userImg" alt="">
            </span>
            <el-dropdown-menu slot="dropdown">
              <div style="text-align:center;" class="user-name">{{username}}</div>
              <el-dropdown-item>22222</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
        <el-main>
          <el-breadcrumb separator="/" class="crumbs">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>查询</el-breadcrumb-item>
          </el-breadcrumb>
          <div>
          
            <router-view :part-info="list" :date="date"></router-view>
            
          </div>
        </el-main>
        <el-footer class="main-footer" height="50px">
          <p>@Sya</p>
        </el-footer>
      </el-container>
    </el-container>

  </div>
</body>
<!-- import Vue before Element -->
<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script src="https://unpkg.com/vue-router/dist/vue-router.js"></script>
<!-- import JavaScript -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script>
  
  const Home = {
        data: function() {
          return {
               //albums:[]
          }
        },
        template: '<div></div>'
        //template: '<el-row><el-col :span="4" v-for="(item,index) in albums" :key="item.path" :offset="index > 0 ? 2 : 0"><el-card ><el-image :src="item.images[0]" class="wocaoimg" style="width: 200px; height: 200px" fit="cover"></el-image><div style="padding: 14px;"><span>{{ item.description }}</span><div class="bottom clearfix"><time class="time">{{ item.time }}</time><router-link :to=" item.path">查看</router-link></div></div></el-card></el-col></el-row>'
  }

  const Albums = {
        data: function() {
          return {
            myimages: [],//图片列表，小图、预览图
          }
        },
        methods: {
          getImages() {
            axios.get("list.aspx?date="+this.date)
              .then(response => {
                this.myimages = response.data.images;
              }).catch(function (error) {
                console.log(error);
              });
          },
          getData2() {
            console.info("俺来了");
          }
        },
        computed: {
          srclist: function(){
            var srcs = [];//大图
            for (j in this.myimages){
              srcs[j] = this.myimages[j].src;
            }
            return srcs;
          }
        },
        mounted () {
          //this.myimages = this.partInfo;console.info(this.$route.path);
            /*axios.get("list.aspx")
            .then(response => {
              console.info(this.$route.path);
              for (i in response.data){
                  if((response.data[i].path) == this.$route.path){
                    this.myimages = response.data[i].images;
                  }
              }
              console.info("初始化页面");
            })
            .catch(function (error) {
                console.log(error);
            });*/
        },
        props:["partInfo","date"],
        
        watch:{
          $route(to, from) {
              //console.info(this.$route.path +"|"+this.partInfo.length);
          },
          partInfo(){
              this.myimages = this.partInfo;
              
          },
          date(){
              this.getImages();
          }
        },
        
        template: '<div class="demo-image__preview"><div class="el-my-card" v-for="(img, index) in myimages" :key="index"><el-image :src="img.src" :preview-src-list="srclist" style="width: 160px; height: 160px;" fit="cover" class="wocaoimg" lazy></el-image><div class="el-my-message">{{index+1}} {{img.description}}</div></div></div>'
  }
  const routes = [
     { path:'/', component: Home},
     { path:'/albums/:id', component:Albums, props:true}
  ]
  const router = new VueRouter({
    routes
  })
  
  var vue = new Vue({
      el: '#app',
      data: function() {
        return { 
          visible: false, 
          tabWidth: 200, 
          username:"图片管理员",
          userImg: '../img/user.png',
          isCollapse: false,
          //时间轴
          timeflow:[],
          photos:[],//时间线中某一天的图片
          date:"",
          //影集
          albums:[],
          //分页数据
          "pages":{"records":0, "prevPage":1, "page":1, "nextPage":1, "lastPage":1, "limit":20},
          "list": []
        }
      },

      methods: {
        handleOpen(key, keyPath) {
          //console.info(key, keyPath);
        },
        handleClose(key, keyPath) {
          //console.info(key, keyPath);
        },
        handleSelect(key, keyPath) {
          //console.info(key, keyPath);
          this.date = key.replace("/albums/","");
        }

      },
      mounted () {
          axios.get("data/timeflow.aspx")
            .then(response => {
              this.timeflow = response.data.timeflow;

              this.date = this.$route.path.replace("/albums/","");

              console.info("初始化页面");
            })
            .catch(function (error) {
                console.log(error);
            });

          console.info("在axios.get后执行才对");
          /* 异步执行了
          for (i in this.albums){
              if((this.albums[i].path) == this.$route.path){
                this.list = this.albums[i].images;
              }
          }
          */
      },
      watch: {
        $route(to, from) {
            for (i in this.albums){
                if((this.albums[i].path) == this.$route.path){
                  this.list = this.albums[i].images;
                }
              }
        }
      },
      router,
      //render (h) { return h(this.ViewComponent) }
    });

  //vue.myimages = ['./img/user1.h1.jpg', './img/user1.h2.jpg', './img/user1.h3.jpg'];
  
  
</script>
<style>
*{
  padding: 0;
  margin: 0;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
.wocaoimg{
  background-color:#f0F0F0;margin: 4px 4px;
}
</style>

</html>