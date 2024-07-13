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
      <el-pagination background layout="prev, pager, next, jumper" :total="search.total" v-model:current-page="search.page"
        @current-change="handleCurrentChange" />
    </div>

    <div class="el-table--fit el-table--border el-table--group el-table--enable-row-hover el-table el-table--layout-fixed is-scrolling-none" style="width: 100%;" data-prefix="el">
      <div class="el-table__inner-wrapper">
        <div class="1el-table__header-wrapper">
          <table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
            <thead>
            <tr>
              <th class="el-table_1_column_1 is-leaf el-table__cell"><div class="cell">ID</div></th>
              <th class="el-table_1_column_2 is-leaf el-table__cell"><div class="cell">MD5</div></th>
              <th class="el-table_1_column_3 is-leaf el-table__cell"><div class="cell">名称</div></th>
              <th class="el-table_1_column_4 is-leaf el-table__cell"><div class="cell">日期</div></th>
              <th class="el-table_1_column_5 is-leaf el-table__cell"><div class="cell">描述</div></th>
              <th class="el-table_1_column_6 is-leaf el-table__cell"><div class="cell">副本</div></th>
              <th class="el-table_1_column_7 is-leaf el-table__cell"><div class="cell">查看</div></th>
              <th class="el-table_1_column_8 is-leaf el-table__cell"><div class="cell">标签</div></th>
              <th class="el-table_1_column_9 is-leaf el-table__cell"><div class="cell">修改</div></th>
              <th class="el-table_1_column_10 is-leaf el-table__cell"><div class="cell">修改</div></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(image, index) in images2">
              <td class="el-table_1_column_1 el-table__cell"><div class="cell">{{image.id}}</div></td>
              <td class="el-table_1_column_2 el-table__cell"><div class="cell">{{image.md5}}</div></td>
              <td class="el-table_1_column_3 el-table__cell"><div class="cell">{{image.name}}</div></td>
              <td class="el-table_1_column_4 el-table__cell"><div class="cell">{{image.time}}</div></td>
              <td class="el-table_1_column_5 el-table__cell"><div class="cell">{{image.description}}</div></td>
              <td class="el-table_1_column_6 el-table__cell"><div class="cell">{{decodeURI(image.path)}}</div></td>
              <td class="el-table_1_column_7 el-table__cell"><div class="cell"><img :src="showPath(index)" width="100px" height=""/></div></td>
              <td class="el-table_1_column_8 el-table__cell"></td>
              <td class="el-table_1_column_9 el-table__cell"><div class="cell"><a :href="'edit.aspx?id='+image.id" target="_blank">修改</a></div></td>
              <td class="el-table_1_column_10 el-table__cell">
                <div class="cell"><el-button type="primary" plain @click="dialogVisible = true">编辑</el-button></div>
              </td>
            </tr>
            </tbody>
            <%-- <c:forEach items="${list}" var="v" varStatus="vs">
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
        </div>
      </div>
    </div>

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
        <el-button type="primary" @click="dialogVisible = false">
          提交
        </el-button>
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
    
    const formLabelWidth = '140px';
    
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