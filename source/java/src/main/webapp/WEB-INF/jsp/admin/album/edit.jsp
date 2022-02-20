<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../menu.jsp"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0">
  <title>图片管理</title>
</head>
<body>
<form method="post">
  <input type="text" name="id" value="<c:out value='${album.id}'/>"/>
  <div>名称：<input type="text" name="name" value="<c:out value='${album.name}'/>"/></div>
  <div>描述：<input type="text" name="description" value="<c:out value='${album.description}'/>" style="width:400px"/></div>
  <div>给照片打勾</div>
  <div id="app">
    <div class="el-my-card" v-for="(img, index) in photos" :key="index">
    <input type="checkbox" name="index">
    {{img.name}}
    <img :src="'../image/preView2.aspx?path='+img.path" width="100px"/>
    <input type="text" name="images_id" v-model="img.id"/>
    </div>
  </div>
  <input type="submit" value="提交"/>
</form>
</body>
<!-- import Vue before Element -->
<script src="../../js/vue.js"></script>
<script type="x-template" id="albums">
<div>test</div>
</script>
<script>

  var vue = new Vue({
    el: '#app',
    data: function() {
      return {
        photos:[<c:forEach items="${album.images}" var="v" varStatus="vs">{
          "id":"<c:out value='${v.id}'/>",
          "name":"<c:out value='${v.name}'/>",
          "path":"<c:out value='${v.URLEncoderPath}' />"
          },</c:forEach>
        ],
        "pages":{"records":0, "prevPage":1, "page":1, "nextPage":1, "lastPage":1, "limit":20},
      }
    }
  });
</script>

</html>