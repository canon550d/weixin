<%@ page language="java" contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../header.jsp"%>
<section class="section"><div class="container">
<a href="add.aspx">add</a>
<a href="addImage.aspx">addImage</a>
<br/>
<div class="columns">
  <c:forEach items="${gallery}" var="g" varStatus="status">
  <div class="column">
    <figure class="image is-128x128">
      <a href="view.aspx?id=${g.id}"><img src="http://bulma.io/images/placeholders/128x128.png"></a>
      <p>${g.intro}</p>
      <p><a href="edit.aspx?id=${g.id}">edit</a></p>
    </figure>
  </div>
  </c:forEach>
</div>
</div></section>
<%@ include file="../footer.jsp"%>