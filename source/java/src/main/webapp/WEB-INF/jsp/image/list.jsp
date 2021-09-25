<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
{
	"name" : "2020-11-12",
	"icon" : "el-icon-folder",
	"description" : "123",
	"path" : "/albums/2020-11-12",
	"time" : "2020-11-13",
	"images" : [ <c:forEach items="${list}" var="v" varStatus="vs">{
		"name" : "<c:out value='${v.name}' />",
		"src" : "/discovery/admin/image/preView.aspx?id=<c:out value="${v.id}" />",
		"cache" : "/discovery/admin/image/preView2.aspx?path=<c:choose><c:when test="${v.cache==null}"><c:out value="${v.path}" /></c:when><c:otherwise><c:out value="${v.cache}" /></c:otherwise></c:choose>",
		"time" : "<fmt:formatDate value="${v.time}" pattern="yyyy-MM-dd HH:mm"/>",
		"rate" : <c:choose><c:when test="${v.rate==null}">0</c:when><c:otherwise><c:out value='${v.rate}' /></c:otherwise></c:choose>,
		"description" : "<c:out value="${v.description}" />"
	}<c:if test="${!vs.last}">,</c:if></c:forEach> ]
}