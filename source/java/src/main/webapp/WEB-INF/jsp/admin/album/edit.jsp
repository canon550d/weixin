<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../menu.jsp"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0">
  <title>图片管理</title>
</head>
<body>
<form method="post">
  <input type="text" name="id" value="<c:out value='${album.id}'/>"/>
  <div>名称：<input type="text" name="name" value="<c:out value='${album.name}'/>"/></div>
  <div>描述：<input type="text" name="description" value="<c:out value='${album.description}'/>" style="width:400px"/></div>
  <input type="text" name="images_id" value="2"/>
  <input type="text" name="images_id" value="3"/>
  <c:forEach items="${album.images}" var="v" varStatus="vs">
  <c:out value='${v.name}'/>
  </c:forEach>
  <input type="submit" value="提交"/>
</form>
</body>
</html>