<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../menu.jsp"%>

<a target="_blank" href="create.aspx">添加</a>
<table border="1">
  <tr>
    <td>ID</td>
    <td>名称</td>
    <td>描述</td>
    <td>修改</td>
  </tr>
<c:forEach items="${list}" var="v" varStatus="vs">
  <tr>
    <td><c:out value="${v.id}" /></td>
    <td><c:out value="${v.name}" /></td>
    <td><c:out value="${v.description}" /></td>
    <td><a href="edit.aspx?id=${v.id}">修改</a></td>
  </tr>
</c:forEach>
</table>

<div class="page">
  <a href="?page=1">第一页</a>
  <a href="?page=<c:out value='${page.previous}'/>">上一页</a>
  <a href="?page=<c:out value='${page.next}'/>">下一页</a>
  <a href="?page=<c:out value='${page.last}'/>">最后一页</a>
</div>