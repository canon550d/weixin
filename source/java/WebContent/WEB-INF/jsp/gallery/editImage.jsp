<%@ page language="java" contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../header.jsp"%>
<section class="section"><div class="container"><div class="columns"><div class="column"><form action="edit.aspx">
<input type="hidden" name="id" value="${image.id}"/>
<div>图片地址：<input type="text" name="path" value='${image.path}' /></div>
<div>图片类型：<input type="text" name="type" value='${image.type}' /></div>
<div>简一一介：<input type="text" name="intro" value='${image.intro}' /></div>
<div>相一一册：<c:forEach items="${galleries}" var="gallery" varStatus="status">
<label><input type="radio" name="gid" value="${gallery.id}" <c:if test="${gallery.id == image.gallery.id}">checked</c:if> />${gallery.name}</label>
</c:forEach></div>
<input type="submit" value="提交"/>
</form></div></div>
<%@ include file="../footer.jsp"%>