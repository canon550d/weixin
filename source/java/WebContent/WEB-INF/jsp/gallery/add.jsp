<%@ page language="java" contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../header.jsp"%>
<section class="section"><div class="container"><div class="columns"><div class="column"><form action="add.aspx">
<input type="hidden" name="id" value="${gallery.id}"/>
<div>???ƣ?<input type="text" name="name" value='${gallery.name}' /></div>
<div>???飺<input type="text" name="intro" value='${gallery.intro}' /></div>
<input type="submit" value="?ύ"/>
</form></div></div>
<%@ include file="../footer.jsp"%>