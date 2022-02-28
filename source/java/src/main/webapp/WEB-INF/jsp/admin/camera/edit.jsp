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
  <input type="text" name="id" value="<c:out value='${camera.id}'/>"/>
  <div>品牌：<input type="text" name="maker" value="<c:out value='${camera.maker}'/>"/></div>
  <div>型号：<input type="text" name="model" value="<c:out value='${camera.model}'/>" style="width:500px"/></div>
  <div>类型：<input type="text" name="type" value="<c:out value='${camera.type}'/>" style="width:500px"/></div>
  <div>路径：<input type="text" name="path" value="<c:out value='${camera.path}'/>" style="width:500px"/></div>
  <div>描述：<input type="text" name="description" value="<c:out value='${camera.description}'/>" style="width:400px"/></div>
  <div>大小：<input type="text" name="size" value="<c:out value='${camera.size}'/>" style="width:500px"/></div>
  <div>长度：<input type="text" name="path_length" value="<c:out value='${camera.path_length}'/>" style="width:500px"/></div>
  <input type="submit" value="提交"/>
</form>
</body>
</html>