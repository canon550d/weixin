<%@ page language="java" contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="header.jsp"%>
<section class="section"><div class="container"><div class="columns"><div class="column"><form action="add.aspx">
<input type="hidden" name="id" value="${article.id}"/>
<div>文件名称：<input type="text" name="fileName" value='${article.fileName}' /></div>
<div>更新时间：<input type="text" name="time" value='<fmt:formatDate value="${article.time}" pattern="yyyy-MM-dd HH:mm:ss" />' /></div>
<div>文章标题：<input type="text" name="title" value='${article.title}' /></div>
<div>文章摘要：
	<div><textarea name="description" style="width:780px;height:80px;">${article.description}</textarea></div>
</div>
<div>文章内容：
	<textarea id="container" name="content" style="width:780px;height:240px;"><c:out value='${article.content}' /></textarea>
</div>
<!-- <script type="text/plain" id="myEditor" style="width:780px;height:240px;"></script> -->
<div>标签：<input type="text" name="tags" value='${article.tags}' /></div>
<input type="submit" value="提交"/>
</form></div>
<div class="column">
<figure class="image is-128x128">
  <img class="gallery" src="http://bulma.io/images/placeholders/128x128.png">
</figure>
</div>
</div></div></section>

  <script type="text/javascript">
  $(document).ready(function() {
    //实例化编辑器
    var um = UM.getEditor('container');
    //um.setContent('${article.content}', true);
    
    $("#wiki").submit(function(e){
        $("#content").val(UM.getEditor('myEditor').getContent());
    });
    $(".gallery").click(function() {
        um.setContent('<img src="'+$(this).attr("src")+'" />', true)
    });
  });
  </script>
<%@ include file="footer.jsp"%>