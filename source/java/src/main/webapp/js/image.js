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

  const Search = {
        data: function() {return {
          myimages: [],//图片列表，小图、预览图
          bigImages:[],
          keyword:""
        }},
        methods: {
          getImages() {console.info("228:"+this.keyword);
            axios.get("list.aspx?keyword="+encodeURI(this.keyword))
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
          this.keyword = this.$route.path.replace("/search/","");
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
              this.keyword = val.replace("/search/","");
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
     { path:'/images/:date', component:Images, props:true},
     { path:'/search/:keyword', component:Search, props:true}
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
          "list": [],
          input1: '',
          input2: '',
          input3: '',
          select: ''
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
        },
        onSearch(key, keyPath) {
          console.info(this.input3);
          console.info(this.$route.path);
          this.$router.push({path:'/search/'+this.input3});
          console.info(this.$route.path);
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
  
  