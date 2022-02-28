<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../menu.jsp"%>

<a target="_blank" href="create.aspx">添加</a>
<a target="_blank" href="scan.aspx">扫描</a>
<div id="app">
<select @change="selectFn($event)">
  <option v-for="item in items" :value="item.id">{{item.name}}</option>
</select>
<div class="page">
  <a href="?page=1">第一页</a>
  <a href="?page=<c:out value='${page.previous}'/>">上一页</a>
  <a href="?page=<c:out value='${page.next}'/>">下一页</a>
  <a href="?page=<c:out value='${page.last}'/>">最后一页</a>
</div>
</div>
<table border="1">
  <tr>
    <td>ID</td>
    <td>名称</td>
    <td>日期</td>
    <td>描述</td>
    <td>路径</td>
    <td>输出</td>
    <td>查看</td>
    <td>修改</td>
  </tr>
<c:forEach items="${list}" var="v" varStatus="vs">
  <tr>
    <td><c:out value="${v.id}" /></td>
    <td><c:out value="${v.name}" /></td>
    <td><fmt:formatDate value="${v.time}" pattern="yyyy-MM-dd"/></td>
    <td><c:out value="${v.description}" /></td>
    <td><c:out value="${v.path}" /></td>
    <td><c:forEach items="${v.exports}" var="ev" varStatus="evs"><c:out value="${ev.path}" /><br/></c:forEach></td>
    <td>
      <c:if test='${v.exportsIsEmpty}'><img src="preView2.aspx?path=<c:out value='${v.URLEncoderPath}' />" width="100px" height=""/></c:if>
      <c:if test='${v.exportsIsNotEmpty}'><img src="preView2.aspx?path=<c:out value='${v.firstExport.URLEncoderPath}' />" width="100px" height=""/></c:if>
    </td>
    <td><a href="edit.aspx?id=${v.id}" target="_blank">修改</a></td>
  </tr>
</c:forEach>
</table>

<div class="page">
  <a href="?page=1<c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if>">第一页</a>
  <a href="?page=<c:out value='${page.previous}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if>">上一页</a>
  <a href="?page=<c:out value='${page.next}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if>">下一页</a>
  <a href="?page=<c:out value='${page.last}'/><c:if test='${camera_id!=null}'>&camera_id=<c:out value='${camera_id}'/></c:if>">最后一页</a>
</div>

<script src="../../js/vue.js"></script>
<script>
var vue = new Vue({
    el: '#app',
    data: function() {
        return {
            items: [{'id':0, 'name':'全部'}<c:forEach items="${cameras}" var="v" varStatus="vs">,{'id':<c:out value='${v.id}' />, 'name':'<c:out value="${v.model}" />'}</c:forEach>]
        }
    },
    methods: {
        selectFn(e) {
            //window.location.href='';
            var url = window.location.origin + window.location.pathname;
            url = url + "?camera_id="+ e.target.value
            window.location.href = url;
        }
    }
});
</script>