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
          <div style="width:800px; float:left;height: 60px; padding-top:8px;">
            
            <el-input placeholder="请输入内容" v-model="input3" class="input-with-select">
              <el-select v-model="select" slot="prepend" placeholder="请选择">
                <el-option label="照片" value="1"></el-option>
                <el-option label="活动" value="2"></el-option>
              </el-select>
              <el-button slot="append" icon="el-icon-search" @click="onSearch"></el-button>
            </el-input>
            
          </div>
          
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
        <i v-if="img.camera_type=='SLR'||img.camera_type=='DC'" class="el-icon-camera"></i>
        <i v-else-if="img.camera_type=='mobile'" class="el-icon-mobile"></i>
        <div style="margin-left: 2px;font-size:12px;display:inline-block;">{{img.time}}</div></div>
      <el-rate v-model="img.rate"></el-rate>
      
      {{img.description}}
    </div>
  </div>

</div>
</script>
<script src="../js/image.js"></script>
<script>

</script>
<style>
*{
  padding: 0;
  margin: 0;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
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