<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
{"timeflow":[<c:forEach items="${list}" var="v" varStatus="vs">"<fmt:formatDate value="${v}" pattern="yyyy-MM-dd"/>"<c:if test="${!vs.last}">,</c:if></c:forEach>]}