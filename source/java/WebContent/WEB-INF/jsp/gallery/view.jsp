<%@ page language="java" contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../header.jsp"%>
<section class="section"><div class="container">
<a href="addImage.aspx">addImage</a>
<div class="columns">
  <c:forEach items="${gallery.images}" var="i" varStatus="status">
  <div class="column">
    <figure class="image is-128x128">
      <img src="${i.path}">
      <p>${i.intro}</p>
      <p><a href="image/edit.aspx?gid=${gallery.id}&id=${i.id}">edit</a>
      <a href="image/delete.aspx?gid=${gallery.id}&id=${i.id}">delete</a></p>
    </figure>
  </div>
  </c:forEach>
</div>
</div></section>
<%@ include file="../footer.jsp"%>