<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>添加新异常</title>
</head>
<body>
<h2>添加新异常 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a href="list">异常列表</a></h2>
<form method="post">
<table border=1 width=700 >
   <tr>
       <td>类型:</td>
       <td>
        SP:<select name="sp">
                   <option value="0">框架级异常</option>
                   <c:forEach items="${sps}" var="s">
                   <option value="${s.id}">${s.sp_name}</option>
                  </c:forEach>
               </select>
        异常层级:<select name="level">
                   <c:forEach items="${levels}" var="l">
                   <option value="${l.value}">${l.name}</option>
                  </c:forEach>
               </select>
        异常类型:<select name="type">
                <c:forEach items="${types}" var="t">
                <option value="${t.value}">${t.name}</option>
               </c:forEach>
            </select>


       </td>
   </tr>
   <tr>
       <td>异常信息:</td>
       <td>
          <textarea name="message" rows="4" cols="50"></textarea>
       </td>
   </tr>
   <tr>
          <td>&nbsp;</td>
          <td>
            <input type="submit"></input>
          </td>
      </tr>
</table>
</form>
</body>
</html>