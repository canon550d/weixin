<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../menu.jsp"%>
<a target="_blank" href="create.aspx">添加</a>
<table border="1">
  <tr>
    <td>ID</td>
    <td>类型</td>
    <td>品牌</td>
    <td>型号</td>
    <td>数量</td>
    <td>大小</td>
    <td>未缓存</td>
    <td>路径</td>
    <td>描述</td>
    <td>修改</td>
  </tr>
<c:forEach items="${list}" var="v" varStatus="vs">
  <tr>
    <td><c:out value="${vs.index+1}" /></td>
    <td><c:out value="${v.type}" /></td>
    <td><c:out value="${v.maker}" /></td>
    <td><div style="width:300px;white-space:nowrap;overflow:hidden"><c:out value="${v.model}" /></div></td>
    <td><c:forEach items="${data}" var="d" varStatus="ds">
      <c:if test="${d.id==v.id}"><c:out value="${d.count}" /></c:if>
      </c:forEach></td>
    <td><c:out value="${v.size}" /></td>
    <td><c:forEach items="${data2}" var="d2" varStatus="ds2">
      <c:if test="${d2.id==v.id}"><c:out value="${d2.count}" /></c:if>
      </c:forEach></td>
    <td><c:out value="${v.path}" /></td>
    <td><c:out value="${v.description}" /></td>
    <td><a href="edit.aspx?id=${v.id}" target="_blank">修改</a>
      <a href="../image/scan4camera.aspx?id=${v.id}" target="_blank">扫描</a>
      <a href="../image/move.aspx?id=${v.id}" target="_blank">整理</a>
      <a href="../image/cache.aspx?id=${v.id}" target="_blank">缓存</a></td>
  </tr>
</c:forEach>
  <tr>
    <td>合计</td>
    <td></td>
    <td></td>
    <td></td>
    <td><c:out value="${total}" /></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
</table>
<div>
<div>修改：修改相机的文字描述等</div>
<div>扫描：对相机的目录扫描，如有数据库中没有的新照片，加入数据库</div>
<div>整理：对已经扫描到数据库中的照片按照拍摄时间排列，并按100个照片存放到文件夹</div>
<div>缓存：对已经扫描到数据库中的照片生成低分辨率缩略图</div>
</div>