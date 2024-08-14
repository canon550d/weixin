<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0" />
  <!-- import CSS -->
  <link rel="stylesheet" href="http://192.168.28.34/css/element-plus-index.css">
  <title>图片管理</title>
</head>
<body>
<%@ include file="../menu.jsp"%>

  <a target="_blank" href="create.aspx">添加</a> 
  <a target="_blank" href="scan.aspx">扫描</a>



    <div class="page">
      <a href="?page=1<c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">第一页</a>
      <a href="?page=<c:out value='${page.previous}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">上一页</a>
      <a href="?page=<c:out value='${page.next}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">下一页</a>
      <a href="?page=<c:out value='${page.last}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">最后一页</a>
    </div>

<div id="app"><el-container>
  <el-header>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false" @select="handleSelect">
      <el-menu-item index="1">相机</el-menu-item>
      <el-menu-item index="2">相片</el-menu-item>
      <el-menu-item index="3">相册</el-menu-item>
      <el-sub-menu index="4">
        <template #title>Workspace</template>
        <el-menu-item index="2-1">item one</el-menu-item>
        <el-menu-item index="2-2">item two</el-menu-item>
        <el-menu-item index="2-3">item three</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="5" disabled>Info</el-menu-item>
      <el-menu-item index="6">Orders</el-menu-item>
    </el-menu>
  </el-header>
  <el-main>
    
    <el-form :model="form" label-width="auto" style="max-width: 600px">
    
      <el-form-item label="相机">
        <el-select v-model="camera_id" @change="selectFn($event)">
          <el-option v-for="camera in cameras" :key="camera.id" :label="camera.name" :value="camera.id" />
        </el-select>
      </el-form-item>
    </el-form>
    
    <div style="margin-bottom: 16px;">
      <el-button>添加</el-button>
    </div>

    <div style="margin-bottom: 16px;">
      <el-pagination background layout="prev, pager, next, jumper" :total="search.total" v-model:current-page="search.page"
        @current-change="handleCurrentChange" />
    </div>

    <el-table border :data="images2">
      <el-table-column prop="id" label="ID" width="58"></el-table-column>
      <el-table-column prop="md5" label="MD5" width="400"></el-table-column>
      <el-table-column prop="name" label="名称" width="165"></el-table-column>
      <el-table-column prop="time" label="日期" width="227"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column label="路径" width="290">
        <template #default="scope">{{decodeURI(scope.row.path)}}</template>
      </el-table-column>
      <el-table-column prop="date" label="查看" width="173">
        <template #default="scope">
          <img :src="showPath(scope.$index)" width="100px" height=""/>
        </template>
      </el-table-column>
      <el-table-column prop="date" label="星级">
        <template #default="scope">
          <el-rate v-model="scope.row.rate"></el-rate>
        </template>
      </el-table-column>
      <el-table-column prop="date" label="标签" width="105">
        <template #default="scope">
          <el-tag type="primary">Tag 1</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="date" label="修改" width="118">
        <template #default="scope">
          <el-button type="primary" plain @click="handleView(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    

    <el-dialog v-model="dialogVisible" title="编辑" width="500" :before-close="handleClose">
      <el-form :model="form">
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form :model="form">
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="form.path" autocomplete="off" />
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="form.description" autocomplete="off" />
        </el-form-item>
        <el-form-item label="时间" :label-width="formLabelWidth">
          <el-input v-model="form.time" autocomplete="off" />
        </el-form-item>
        <el-form-item label="评分" :label-width="formLabelWidth">
          <el-input v-model="form.rate" autocomplete="off" />
        </el-form-item>
        <el-form-item label="相机" :label-width="formLabelWidth">
          <el-input v-model="form.camera.id" autocomplete="off" />
        </el-form-item>
      </el-form>
      <footer class="el-dialog__footer">
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleEdit()">提交</el-button>
        </div>
      </footer>
    </el-dialog>
  </el-main>
</el-container></div>

<div class="page">
  <a href="?page=1<c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">第一页</a>
  <a href="?page=<c:out value='${page.previous}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">上一页</a>
  <a href="?page=<c:out value='${page.next}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">下一页</a>
  <a href="?page=<c:out value='${page.last}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">最后一页</a>
</div>



</body>
<!-- Vue 3 -->
<script src="http://192.168.28.34/js/vue3.js"></script>
<script src="http://192.168.28.34/js/axios.min.js"></script>
<!-- element-plus -->
<script src="http://192.168.28.34/js/element-plus.js"></script>
<script src="http://192.168.28.34/js/element-plus-locale-zh-cn.js"></script>
<script src="http://192.168.28.34/js/element-plus-icons-vue.js"></script>

<script>
const { createApp, onMounted, ref, reactive } = Vue
const { ElTable, ElTableColumn, ElMessageBox } = ElementPlus;

const App = {
  setup() {
    const message = ref('Hello Vue!');
    
    const activeIndex = ref('1');
    const activeIndex2 = ref('1');
    const handleSelect = (key, keyPath) => {
      console.log(key, keyPath);
    }

    const tableData = ref([]);
    
    const dialogVisible = ref(false);
    
    const handleClose = () => {
      ElMessageBox.confirm('Are you sure to close this dialog?')
        .then(() => {
          
        })
        .catch(() => {
        // catch error
        })
    };
    
    const handleView = (image) => {
        dialogVisible.value = true;
        axios.post("read.aspx", "id="+image.id).then(resp=>{
        /*
        axios.post("read.aspx", "id="+id).then(resp=>{
            form.name = resp.data.data.name;
            form.path = decodeURI(resp.data.data.path);
            form.cache = resp.data.data.cache;
            form.description = resp.data.data.description;
            form.time = resp.data.data.time;
            form.rate = resp.data.data.rate;
        });
        */
        });
        form.id = image.id;
        form.name = image.name;
        form.path = decodeURI(image.path);
        form.cache = image.cache;
        form.description = image.description;
        form.time = image.time;
        form.rate = image.rate;
    }
    
    const handleEdit = () => {
        axios.post("edit.aspx", form).then(resp=>{
            const result = resp.data.data;
            if (result) {
                ElementPlus.ElMessage.success('修改成功');
                console.info(this);
                dialogVisible.value = false;
            } else {
                ElementPlus.ElMessage.error('修改失败');
            }

        });
    }
    
    const form = reactive({
      name: '',
      path: '',
      cache: '',
      description: '',
      time: '',
      rate: 0,
      src: '',
      camera: {
        id: '',
        name: ''
      }
    })
    
    const formLabelWidth = '140px';
    
    return {
      message, tableData, dialogVisible,
      handleClose, handleView, handleEdit,
      form, formLabelWidth,
      activeIndex, activeIndex2, handleSelect
    }
  },
  //vue2传统写法还是支持的
  data() {
    return {
      message: "Hello Element Plus",
      camera_id : <c:out value='${camera_id}'/>,
      cameras: [],
      images:[<c:forEach items="${list}" var="v" varStatus="vs">"<c:out value='${v.bucket.URLEncoderPath}' /><c:out value='${v.camera.URLEncoderPath}' /><c:out value='${v.URLEncoderPath}' />%5c<c:out value='${v.name}' />",</c:forEach>],
      images2:[],
      search:{
        "previous":1,
        "page":<c:out value='${page.page}'/>,
        "next":1,
        "last":1,
        "pageSize":10,
        "total":0
      }
    };
  },
  methods: {
    showPath(i){
      return "preView2.aspx?path="+this.images[i];
    },
    cameralist() {
      axios.post("../camera/list.aspx").then(resp=>{
        this.cameras.push({'id':0, 'name':'全部'});
        const camears = resp.data.data.list;
        for (var i=0;i<camears.length;i++) {
          this.cameras.push({'id':camears[i].id, 'name':camears[i].model});
        }
      }).catch(resp=>{
          console.log('failure');
      });
      
    },
    selectFn(e) {
        
    },
    list() {
      axios.post("list.aspx", "page="+this.search.page).then(resp=>{
        this.images2 = resp.data.data.list;
        this.search.previous = resp.data.data.previous;
        this.search.page = resp.data.data.page;
        this.search.next = resp.data.data.next;
        this.search.last = resp.data.data.last;
        this.search.pageSize = resp.data.data.pageSize;
        this.search.total = resp.data.data.total;
        for (let i=0;i<resp.data.data.list.length;i++){
          this.images[i] = resp.data.data.list[i].src;
        }
      }).catch(resp=>{
        console.log('failure');
      });
    },
    handleCurrentChange (val) {
      this.search.page = val;
      this.list();
    },
    handlePrevClick (val) {
      console.info(val);
    },
    handleNextClick (val) {
      console.info(val);
    }
  },
  mounted () {
    this.cameralist();
    this.list();
  }
};
const app = createApp(App);
app.use(ElementPlus, {
    locale: ElementPlusLocaleZhCn,
});
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount("#app");
<%--
var vue = new Vue({
    el: '#app',
    data: function() {
        return {
            camera_id : <c:out value='${camera_id}'/>,
            cameras: [{'id':0, 'name':'全部'}<c:forEach items="${cameras}" var="v" varStatus="vs">,{'id':<c:out value='${v.id}' />, 'name':'<c:out value="${v.model}" />'}</c:forEach>],
            images:[<c:forEach items="${list}" var="v" varStatus="vs">"<c:out value='${v.bucket.URLEncoderPath}' /><c:out value='${v.camera.URLEncoderPath}' /><c:out value='${v.URLEncoderPath}' />%5c<c:out value='${v.name}' />",</c:forEach>]
        }
    },
    methods: {
        selectFn(e) {
            //window.location.href='';
            var url = window.location.origin + window.location.pathname;
            if(e.target.value==0){
            } else {
                url = url + "?camera_id="+ e.target.value
            }
            window.location.href = url;
        },
        sshowImage(e, i, path) {
            Vue.set(this.images, i, path);
        },
        showPath(i){
            return "preView2.aspx?path="+this.images[i];
        }
    },
    computed: {
    },
    watch:{
    }
});
--%>
</script>
<style>
.el-menu--horizontal > .el-menu-item:nth-child(3) {
  margin-right: auto;
}
</style>
</html>