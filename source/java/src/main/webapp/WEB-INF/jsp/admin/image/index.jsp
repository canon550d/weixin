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

<div id="app">
  <select v-model="camera_id" @change="selectFn($event)">
    <option v-for="camera in cameras" :value="camera.id">{{camera.name}}</option>
  </select>

  <div class="page">
    <a href="?page=1<c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">第一页</a>
    <a href="?page=<c:out value='${page.previous}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">上一页</a>
    <a href="?page=<c:out value='${page.next}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">下一页</a>
    <a href="?page=<c:out value='${page.last}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">最后一页</a>
  </div>
  
  <div class="example-pagination-block">
    <el-pagination background layout="prev, pager, next, jumper" :total="search.total" v-model:current-page="search.page"
      @current-change="handleCurrentChange" />
  </div>
  
  <table border="1">
    <tr>
      <td>ID</td>
      <td>MD5</td>
      <td>名称</td>
      <td>日期</td>
      <td>描述</td>
      <td>副本</td>
      <td>查看</td>
      <td>标签</td>
      <td>修改</td>
      <td>修改</td>
    </tr>
    <tr v-for="(image, index) in images2">
      <td>{{image.id}}</td>
      <td>{{image.md5}}</td>
      <td>{{image.name}}</td>
      <td>{{image.time}}</td>
      <td>{{image.description}}</td>
      <td>{{decodeURI(image.path)}}</td>
      <td><img :src="showPath(index)" width="100px" height=""/></td>
      <td></td>
      <td><a :href="'edit.aspx?id='+image.id" target="_blank">修改</a></td>
      <td>
        <el-button type="primary" plain @click="dialogVisible = true">编辑</el-button>
      </td>
    </tr><%-- <c:forEach items="${list}" var="v" varStatus="vs">
    <tr>
      <td><c:out value="${v.id}" /></td>
      <td><c:out value="${v.md5}" /></td>
      <td><c:out value="${v.name}" /></td><!--名称-->
      <td><fmt:formatDate value="${v.time}" pattern="yyyy-MM-dd"/></td><!--日期-->
      <td><c:out value="${v.description}" /></td><!--描述-->
      <td>
        <div @click="sshowImage($event, <c:out value="${vs.index}" />, '<c:out value="${v.URLEncoderPath}" />')"><c:out value="${v.path}" /></div><c:forEach items="${v.files}" var="ev" varStatus="evs">
        <div @click="sshowImage($event, <c:out value="${vs.index}" />, '<c:out value="${ev.URLEncoderPath}" />')"><c:out value="${ev.path}" /></div>
      </c:forEach></td><!--副本-->
      <td><img :src="showPath(<c:out value="${vs.index}" />)" width="100px" height=""/></td>
      <td>
        <c:forEach items="${v.labels}" var="la" varStatus="las"><c:out value="${la.name}" /></c:forEach>
        <div><span>+</span><c:forEach items="${labels}" var="la" varStatus="las"><a href="../label/create.aspx?label_id=${la.id}&image_id=${v.id}" target="_blank"><c:out value="${la.name}" /></a></c:forEach></div>
      </td>
      <td><a href="edit.aspx?id=${v.id}" target="_blank">修改</a></td>
    </tr></c:forEach> --%>
  </table>

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
    
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>

</div>

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
<script>
const { createApp, onMounted, ref, reactive } = Vue
const { ElTable, ElTableColumn, ElMessageBox } = ElementPlus;

const App = {
  setup() {
    const message = ref('Hello Vue!');

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
    
    const form = reactive({
      name: '',
      path: '',
      cache: '',
      description: '',
      time: '',
      rate: '',
      camera: {
        id: '',
        name: ''
      }
    })
    
    const formLabelWidth = '140px'
    
    return {
      message, tableData, dialogVisible, handleClose, form, formLabelWidth
      
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
app.use(ElementPlus);
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
</html>