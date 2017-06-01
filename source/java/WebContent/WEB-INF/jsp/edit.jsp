<%@ page language="java" contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="header.jsp"%>
<section class="section"><div class="container"><div class="columns"><div class="column"><form action="edit.aspx">
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
<div class="column is-2 column-gallery">
</div>
<div class="column is-1">
<aside class="menu">
  <ul class="menu-list"><c:forEach items="${galleries}" var="g" varStatus="status">
    <li><a data-gid="${g.id}" >${g.name}</a></li>
  </c:forEach></ul>
</aside>
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
    $(".column-gallery").on('click', 'img', function() {
        um.setContent('<img src="'+$(this).attr("src")+'" />', true)
    });
    $(".menu-list a").click(function() {
        $(".menu-list a").attr("class", "");
        $(this).attr("class", "is-active");
        $.post("gallery/image/list.aspx", { gid: $(this).attr("data-gid"), time: "2pm" }, function(data,status){
            var html = '';console.info(data.length);
            for(var i=0;i<data.length;i++){
                html += '<figure class="image is-128x128">';
                html += '<img class="gallery" src="'+data[i].path+'">';
                html += '</figure>';
            }
            
            $(".column-gallery").html(html);
        },"json");
    });
  });
  </script>
<%@ include file="footer.jsp"%>