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
<c:out value='${image.camera.model}'/>
<form method="post">
  <%-- <input type="text" name="id" value="<c:out value='${image.id}'/>"/> --%>
  <div>名称：<input type="text" name="name" value="<c:out value='${image.name}'/>"/></div>
  <div>地址：<input type="text" name="path" value="<c:out value='${image.path}'/>" style="width:500px"/></div>
  <div>描述：<input type="text" name="description" value="<c:out value='${image.description}'/>" style="width:400px"/></div>
  <div>时间：<input type="text" name="time" value="<fmt:formatDate value="${image.time}" pattern="yyyy-MM-dd HH:mm:ss"/>"/></div>
  <div>评分：<input type="text" name="rate" value="<c:out value='${image.rate}'/>"/></div>
  <div>输出：<c:forEach items="${image.exports}" var="ev" varStatus="evs"><c:out value="${ev.path}" /><br/></c:forEach>
    <input type="text" name="export_path" value=""/>
  </div>
  <input type="submit" value="提交"/>
</form>
</body>
</html>