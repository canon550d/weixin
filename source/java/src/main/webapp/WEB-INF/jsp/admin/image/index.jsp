<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    </tr><c:forEach items="${list}" var="v" varStatus="vs">
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
    </tr></c:forEach>
  </table>
</div>

<div class="page">
  <a href="?page=1<c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">第一页</a>
  <a href="?page=<c:out value='${page.previous}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">上一页</a>
  <a href="?page=<c:out value='${page.next}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">下一页</a>
  <a href="?page=<c:out value='${page.last}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if><c:if test='${orderby!=null}'>&orderby=<c:out value='${orderby}'/></c:if>">最后一页</a>
</div>

<script src="../../js/vue.js"></script>
<script>
var vue = new Vue({
    el: '#app',
    data: function() {
        return {
            camera_id : <c:out value='${camera_id}'/>,
            cameras: [{'id':0, 'name':'全部'}<c:forEach items="${cameras}" var="v" varStatus="vs">,{'id':<c:out value='${v.id}' />, 'name':'<c:out value="${v.model}" />'}</c:forEach>],
            images:[<c:forEach items="${list}" var="v" varStatus="vs">"<c:out value='${v.URLEncoderPath}' />",</c:forEach>]
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
</script>