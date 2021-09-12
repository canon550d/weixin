<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
{
"timeflow":[<c:forEach items="${list}" var="v" varStatus="vs">"<c:out value="${v}"/>"<c:if test="${!vs.last}">,</c:if></c:forEach>],
"timeflow2":[<c:forEach items="${year}" var="v" varStatus="vs">"<c:out value="${v}"/>"<c:if test="${!vs.last}">,</c:if></c:forEach>],
"albums":[<c:forEach items="${albums}" var="v" varStatus="vs">{"id":<c:out value='${v.id}'/>,"name":"<c:out value='${v.name}'/>"}<c:if test="${!vs.last}">,</c:if></c:forEach>]
}