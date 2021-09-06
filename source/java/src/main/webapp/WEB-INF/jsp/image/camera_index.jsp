<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<a target="_blank" href="create.aspx">添加</a>
<table border="1">
  <tr>
    <td>ID</td>
    <td>品牌</td>
    <td>型号</td>
    <td>描述</td>
    <td>修改</td>
  </tr>
<c:forEach items="${list}" var="v" varStatus="vs">
  <tr>
    <td><c:out value="${v.id}" /></td>
    <td><c:out value="${v.maker}" /></td>
    <td><c:out value="${v.model}" /></td>
    <td><c:out value="${v.description}" /></td>
    <td><a href="edit.aspx?id=${v.id}" target="_blank">修改</a></td>
  </tr>
</c:forEach>
</table>