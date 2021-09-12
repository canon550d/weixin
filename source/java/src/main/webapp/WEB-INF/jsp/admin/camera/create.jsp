<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../menu.jsp"%>
<form method="post">
  <div>品牌：<input type="text" name="maker" value="" style="width:500px"/></div>
  <div>型号：<input type="text" name="model" value=""/></div>
  <div>描述：<input type="text" name="description" value="" style="width:400px"/></div>
  <input type="submit" value="提交"/>
</form>