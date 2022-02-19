<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../menu.jsp"%>
<a target="_blank" href="create.aspx">添加</a>
<table border="1">
  <tr>
    <td>ID</td>
    <td>类型</td>
    <td>品牌</td>
    <td>型号</td>
    <td>数量</td>
    <td>未缓存</td>
    <td>路径</td>
    <td>描述</td>
    <td>修改</td>
  </tr>
<c:forEach items="${list}" var="v" varStatus="vs">
  <tr>
    <td><c:out value="${vs.index+1}" /></td>
    <td><c:out value="${v.type}" /></td>
    <td><c:out value="${v.maker}" /></td>
    <td><c:out value="${v.model}" /></td>
    <td><c:forEach items="${data}" var="d" varStatus="ds">
      <c:if test="${d.id==v.id}"><c:out value="${d.count}" /></c:if>
      </c:forEach></td>
    <td><c:forEach items="${data2}" var="d2" varStatus="ds2">
      <c:if test="${d2.id==v.id}"><c:out value="${d2.count}" /></c:if>
      </c:forEach></td>
    <td><c:out value="${v.path}" /></td>
    <td><c:out value="${v.description}" /></td>
    <td><a href="edit.aspx?id=${v.id}" target="_blank">修改</a>
      <a href="../image/cache.aspx?id=${v.id}" target="_blank">缓存</a>
      <a href="../image/move.aspx?id=${v.id}" target="_blank">整理</a></td>
  </tr>
</c:forEach>
  <tr>
    <td>合计</td>
    <td></td>
    <td></td>
    <td></td>
    <td><c:out value="${total}" /></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
</table>