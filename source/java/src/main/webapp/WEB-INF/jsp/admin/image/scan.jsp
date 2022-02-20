<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

路径：
<form method="post">
<input type="text" name="path" value="" style="width:550px"/>
<input type="submit" value="扫描"/>
</form>

<div id="app">
<c:if test="${files!=null}">
扫描到下面的图片
<form method="post" action="savescan.aspx">
bucket_id:<input name="bucket_id" value="1"/>
</br>
<input name="next" value="<c:out value='${next}'/>"/>
<input name="nextPath" value="<c:out value='${path}'/>"/>
<table border="1">
<tr>
    <td><input type="checkbox" :checked="checkedAll" @click="checkAnti"/></td>
    <td>序号</td>
    <td>名称</td>
    <td>拍摄时间</td>
    <td>照相机制造商</td>
    <td>照相机型号</td>
    <td>描述</td>
    <td>路径</td>
    <td>查看</td>
</tr>


<tr v-for="(item,idx) in images">
    <td><input type="checkbox" name="index" :checked="item.checked" @click="checkAntiThis(item)"></td>
    <td>{{idx+1}}</td>
    <td><input type="text" name="name" v-model="item.name" style="width:150px" :disabled="!item.checked"/></td>
    <td><input type="text" name="time" v-model="item.time" style="width:150px" :disabled="!item.checked"/></td>
    <td><input type="text" name="maker" v-model="item.maker" style="width:100px" :disabled="!item.checked"/></td>
    <td><input type="text" name="model" v-model="item.model" style="width:150px" :disabled="!item.checked"/></td>
    <td><input type="text" name="description" value="" style="width:100px" :disabled="!item.checked"/></td>
    <td><input type="text" name="path" v-model="item.path" style="width:500px" :disabled="!item.checked"/></td>
    <td>查看</td>
</tr>
</table>

<input type="submit" value="提交" style="margin-top:20px;">
</form>

</div>

<c:set var="a" value="\\"/>
<c:set var="b" value="\\\\"/>

<script src="../../js/vue.js"></script>
<script>
var vue = new Vue({
    el: '#app',
    data: function() {
        return {
            checkedAll : true, image:"E:\Sya\图片\Image\2015-01-13\IMG_7788.JPG",
            checkArr: [false, false, false, false],
            images:[<c:forEach items="${files}" var="v" varStatus="vs"><c:set var="meta" value="${list[vs.index]}"/>{
                "name":"<c:out value='${v.name}'/>",
                "time":"<c:out value='${meta.time}'/>",
                "maker":"<c:out value='${meta.make}'/>",
                "model":"<c:out value='${meta.model}'/>",
                "description":"",
                "path":"<c:out value='${fn:replace(v.path, a, b)}'/>",
                "checked": <c:if test="${meta.time==null}">false</c:if><c:if test="${meta.time!=null}">true</c:if>
            },</c:forEach>]
        }
    },
    methods: {
        checkAnti() {
            this.checkedAll = !this.checkedAll;
            this.images.forEach(item => {
                item.checked = this.checkedAll;
            });
        },
        checkAntiThis(obj, event) {
            this.images.forEach(item => {
                
                if(obj.name==item.name){
                    item.checked = !item.checked;
                }
            });
        }
    }
});
</script>
</c:if>
