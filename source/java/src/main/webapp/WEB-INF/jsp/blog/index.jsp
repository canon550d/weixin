<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="app">Blog
  <div class="page">
    <a href="?page=1<c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">第一页</a>
    <a href="?page=<c:out value='${page.previous}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">上一页</a>
    <a href="?page=<c:out value='${page.next}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">下一页</a>
    <a href="?page=<c:out value='${page.last}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">最后一页</a>
  </div>
  <table border="1">
    <tr>
      <td>ID</td>
      <td>Blog</td>
    </tr>
    <tr v-for="blog in items.blogs">
      <td>{{blog.id}}</td>
      <td><div>{{blog.note}}</div>
        <div v-for="m in blog.media"><img :src="'../admin/image/preView2.aspx?path='+m.path" width="100px" ></div>
        <div>{{blog.time}}</div>
      </td>
    </tr>
  </table>
</div>

<script src="../js/vue.js"></script>
<script src="../js/axios.min.js"></script>
<script>
var vue = new Vue({
    el: '#app',
    data: function() {
        return {
            items: {"page":{"pageSize":10},"blogs":[]}
        }
    },
    methods: {
        list() {
            axios.post("list.aspx", "page=1").then(resp=>{
                console.log(resp);
                this.items = resp.data;
            }).catch(resp=>{
                console.log('failure');
            });
        }
    },
    mounted: function(){
        this.list();
    }
});
</script>