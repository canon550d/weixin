<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../menu.jsp"%>

<a target="_blank" href="create.aspx">添加</a>
<a target="_blank" href="scan.aspx">扫描</a>
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
    <td><img src="preView.aspx?id=${v.id}" width="100px" height=""/></td>
    <td><a href="edit.aspx?id=${v.id}" target="_blank">修改</a></td>
  </tr>
</c:forEach>
</table>

<div class="page">
  <a href="?page=1">第一页</a>
  <a href="?page=<c:out value='${page.previous}'/>">上一页</a>
  <a href="?page=<c:out value='${page.next}'/>">下一页</a>
  <a href="?page=<c:out value='${page.last}'/>">最后一页</a>
</div>