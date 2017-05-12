<%@ page language="java" contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="header.jsp"%>
<section class="section"><div class="container">
<a href="add.aspx">add</a>
<table class="table">
  <thead>
    <tr>
      <th><abbr title="Position">Pos</abbr></th>
      <th>thumbnail</th>
      <th>title</th>
      <th><abbr title="Played">fileName</abbr></th>
      <th>tags</th>
      <th>act</th>
    </tr>
  </thead>
  <tfoot>
    <tr>
      <th><abbr title="Position">Pos</abbr></th>
      <th>thumbnail</th>
      <th>title</th>
      <th><abbr title="Played">fileName</abbr></th>
      <th>tags</th>
      <th>act</th>
    </tr>
  </tfoot>
  <tbody>
  <c:forEach items="${articles}" var="article" varStatus="status">
    <tr>
      <th>${status.index + 1}</th>
      <th><figure class="image is-24x24"><img  src="${article.thumbnail}"/></figure></th>
      <td><a target="_blank" href="preview.aspx?id=${article.id}" title="">${article.title}</a> <strong></strong></td>
      <td>${article.fileName}</td>
      <td>${article.tags}</td>
      <td><a href="edit.aspx?id=${article.id}" title="">edit</a>
        <a class="delete is-small" href="delete.aspx?id=${article.id}" title=""></a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</div></section>
<%@ include file="footer.jsp"%>