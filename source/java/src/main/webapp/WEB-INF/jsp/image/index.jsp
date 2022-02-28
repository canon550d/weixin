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
        <el-scrollbar><!-- <div class="menu-wrap">  -->
          <div class="isClossTab">相片管理</div>

          <el-menu router :class="'menu'" 
                 :default-active="$route.path"
                 <%--:default-openeds=["1"]--%>
                 class="el-menu-vertical-demo"
                 @select="handleSelect"
                 @open="handleOpen"
                 @close="handleClose"
                 :collapse="false"
                 background-color="#545c64"
                 text-color="#fff"
                 active-text-color="#ffd04b">
            <el-submenu index="1">
              <%--
              <el-menu-item :index="'/albums/'+item" v-for="item in timeflow" :key="'/albums/'+item">
                <i class="el-icon-folder"></i>
                <span slot="title">{{item}}</span>
              </el-menu-item>
              --%>
              <template slot="title">
                <span>相片</span>
              </template>
              <el-submenu :index="year" :key="year" v-for="year in timeflow2">
                <template slot="title">{{year}}</template>
                <el-menu-item :index="'/images/'+item" v-for="item in timeflow" :key="'/images/'+item" v-if="item.indexOf(year)>-1">{{item}}</el-menu-item>
              </el-submenu>
            </el-submenu>
            <el-submenu index="2">
              <span slot="title">相册</span>
              <el-menu-item :index="'/albums/'+item.id" v-for="item in albums" :key="'/albums/'+item.id">
                <i class="el-icon-folder"></i>
                <span slot="title">{{item.name}}</span>
              </el-menu-item>
            </el-submenu>
            <el-submenu index="3">
              <span slot="title">标签</span>
              <el-menu-item :index="'/label/'+item.id" v-for="item in labels" :key="'/label/'+item.id">
                <i class="el-icon-collection-tag"></i>
                <span slot="title">{{item.name}}</span>
              </el-menu-item>
            </el-submenu>

            <!-- <el-menu-item :index="item.path" v-for="item in menuInfo" :key="item.path">
              <i :class="item.icon"></i>
              <span slot="title">{{item.label}}</span>
            </el-menu-item>  -->
          </el-menu>

        </el-scrollbar><!-- </div> -->
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
            <el-breadcrumb-item>照片</el-breadcrumb-item>
          </el-breadcrumb>
          <div>
          
            <router-view :key="randomKey" :part-info="list" :params="params"></router-view>
            
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
<script src="../js/vue.js"></script>
<script src="../js/vue-router.js"></script>
<!-- import JavaScript -->
<script src="../js/index.js"></script>
<script src="../js/axios.min.js"></script>
<script type="x-template" id="albums">
<div class="demo-image__preview">
  <div class="el-my-card" v-for="(img, index) in myimages" :key="index">
    <el-image :src="img.cache" 
        :preview-src-list="bigImages"
        @click="setBigimages(index)"
        style="width: 160px; height: 160px;" fit="cover" class="wocaoimg" lazy></el-image>
    <div class="el-my-message">
      <span v-if="index<9" style="width:30px;display:inline-block;">00{{index+1}}</span>
      <span v-else-if="index<100 && index>=9" style="width:30px;display:inline-block;">0{{index+1}}</span>
      <span v-else style="width:30px;display:inline-block;">{{index+1}}</span>
      <span style="font-size:12px;display:inline-block;">{{img.name}}</span>
      <div style="padding-left:10px;">
        <i v-if="img.camera_type=='camera'||img.camera_type=='DC'" class="el-icon-camera"></i>
        <i v-else-if="img.camera_type=='mobile'" class="el-icon-mobile"></i>
        <div style="margin-left: 2px;font-size:12px;display:inline-block;">{{img.time}}</div></div>
      <el-rate v-model="img.rate"></el-rate>
      
      {{img.description}}
    </div>
  </div>

</div>
</script>
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
            bigImages:[],
            id:1
          }
        },
        methods: {
          getImages() {
            axios.get("list.aspx?album="+this.id)
              .then(response => {
                this.myimages = response.data.images;
                this.bigImages = this.srclist2(0);
              }).catch(function (error) {
                console.log(error);
              });
          },
          setBigimages(index){
            console.info(index);
            this.bigImages = this.srclist2(index);
          },
          srclist2(index) {
            var srcs = [];//大图
            for (j in this.myimages){
              srcs[j] = this.myimages[j].src;
            }
            if (index == 0)
              return srcs;
            let start = srcs.splice(index);
            let remain = srcs.splice(0, index);
            let new_srclist = start.concat(remain);console.info(new_srclist);
            return new_srclist;
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
          console.info("this.route:"+this.$route.path);
          this.id = this.$route.path.replace("/albums/","");
          this.getImages();

        },
        props:["partInfo","params"],
        
        watch:{
          $route(to, from) {
              //console.info(this.$route.path +"|"+this.partInfo.length);
          },
          partInfo(){
              this.myimages = this.partInfo;
              
          },
          params(val){
              console.info("params:"+val);
              this.id = val.replace("/albums/","");
              this.getImages();
          }
        },
        
        template: "#albums"
  }
  const Label = {
        data: function() {
            return {
              myimages: [],//图片列表，小图、预览图
              bigImages:[],
              id:1
            }
          },
          methods: {
            getImages() {
              axios.get("list.aspx?label="+this.id)
                .then(response => {
                  this.myimages = response.data.images;
                  this.bigImages = this.srclist2(0);
                }).catch(function (error) {
                  console.log(error);
                });
            },
            setBigimages(index){
              console.info(index);
              this.bigImages = this.srclist2(index);
            },
            srclist2(index) {
              var srcs = [];//大图
              for (j in this.myimages){
                srcs[j] = this.myimages[j].src;
              }
              if (index == 0)
                return srcs;
              let start = srcs.splice(index);
              let remain = srcs.splice(0, index);
              let new_srclist = start.concat(remain);console.info(new_srclist);
              return new_srclist;
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
            console.info("this.route:"+this.$route.path);
            this.id = this.$route.path.replace("/label/","");
            this.getImages();

          },
          props:["partInfo","params"],
          
          watch:{
            $route(to, from) {
                //console.info(this.$route.path +"|"+this.partInfo.length);
            },
            partInfo(){
                this.myimages = this.partInfo;
                
            },
            params(val){
                console.info("params:"+val);
                this.id = val.replace("/albums/","");
                this.getImages();
            }
          },
          
          template: "#albums"
  }
  const Images = {
        data: function() {return {
          myimages: [],//图片列表，小图、预览图
          bigImages:[],
          date:""
        }},
        methods: {
          getImages() {
            axios.get("list.aspx?date="+this.date)
              .then(response => {
                   this.myimages = response.data.images;
                   this.bigImages = this.srclist2(0);
              }).catch(function (error) {
                   console.log(error);
              });
          },
          setBigimages(index){
            console.info(index);
            this.bigImages = this.srclist2(index);
          },
          srclist2(index) {
            var srcs = [];//大图
            for (j in this.myimages){
              srcs[j] = this.myimages[j].src;
            }
            if (index == 0)
              return srcs;
            let start = srcs.splice(index);
            let remain = srcs.splice(0, index);
            let new_srclist = start.concat(remain);console.info(new_srclist);
            return new_srclist;
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
          console.info("this.route:"+this.$route.path);
          this.date = this.$route.path.replace("/images/","");
          this.getImages();
        },
        watch:{
          $route(to, from) {
              //console.info(this.$route.path +"|"+this.partInfo.length);
          },
          partInfo(){
              this.myimages = this.partInfo;
              console.info(this.partInfo);
          },
          params(val){
              console.info("params:"+val);
              this.date = val.replace("/images/","");
              this.getImages();
          }
        },
        props:["partInfo","params"],
        template: "#albums"
  }
  const routes = [
     { path:'/', component: Home},
     { path:'/albums/:id', component:Albums, props:true},
     { path:'/label/:id', component:Label, props:true},
     { path:'/images/:date', component:Images, props:true}
  ]
  const router = new VueRouter({
    routes
  })
  
  var vue = new Vue({
      el: '#app',
      data: function() {
        return { 
          randomKey : "",
          visible: false, 
          tabWidth: 200, 
          username:"图片管理员",
          userImg: '../img/user.png',
          isCollapse: false,
          //时间轴
          timeflow:[],
          timeflow2:[],
          album:[],
          photos:[],//时间线中某一天的图片
          params:"",
          date:"",//准备删除
          //影集
          albums:[],
          labels:[],
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
          //this.date = key.replace("/images/","");
          var reg=new RegExp("^/albums/");
          if(key.length==18 || reg.test(key)){
            this.params = key;
            this.randomKey = Math.random();
          }
        }

      },
      mounted () {
          axios.get("data/timeflow.aspx")
            .then(response => {
              this.timeflow = response.data.timeflow;
              this.timeflow2 = response.data.timeflow2;
              this.albums = response.data.albums;
              this.labels = response.data.labels;
              //this.date = this.$route.path.replace("/images/","");
              //this.params = this.$route.path;

              console.info("菜单栏参数赋值完毕");
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
            console.info(this.params);
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