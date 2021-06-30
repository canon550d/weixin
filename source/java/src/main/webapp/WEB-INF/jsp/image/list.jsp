<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
	"name" : "2020-11-12",
	"icon" : "el-icon-folder",
	"description" : "123",
	"path" : "/albums/2020-11-12",
	"time" : "2020-11-13",
	"images" : [ <c:forEach items="${list}" var="v" varStatus="vs">{
		"src" : "/discovery/admin/image/preView.aspx?id=<c:out value="${v.id}" />",
		"description" : "<c:out value="${v.description}" />"
	}<c:if test="${!vs.last}">,</c:if></c:forEach> ]
}