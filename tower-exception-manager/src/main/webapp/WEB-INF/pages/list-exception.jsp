<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>异常列表</title>
</head>
<body>
<h2>异常定义列表&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a href="add">添加新异常</a></h2>
<form>
<table>
<tr>
    <td><input name="searchCode" width="100"> <input type="submit" value="搜索"></td>
</tr>
</table>
</form>
<table border=1>
   <tr>
        <th>编号</th>
        <th>枚举值</th>
        <th>SP名称</th>
        <th>异常层级</th>
        <th>异常类型</th>
        <th>异常信息</th>
   </tr>
   <c:forEach items="${list}" var="exception">
   <tr>
       <td>${exception.fullCode}</td>
        <td>${exception.enumKey}</td>
        <td>${exception.ajkSoaSp.sp_name}</td>
        <td>${exception.exceptionLevel.name}</td>
       <td>${exception.exceptionType.name}</td>
       <td>${exception.message}</td>
   </tr>
   </c:forEach>
</table>
</body>
</html>