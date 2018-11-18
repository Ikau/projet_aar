<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ikau
  Date: 14/10/2018
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tests backend</title>
</head>
<body>
    <h1>Projets</h1>
    <c:forEach items="${projets}" var="p">
        <br/>Intitule : ${p.getIntitule()}
        <br/>Resume : ${p.getResume()}
        <br/>Description : ${p.getDescription()}
        <br/>Palliers
        <div>
            <c:forEach items="${p.getPalliers()}" var="pal">
                <br/> Intitule : ${pal.getIntitule()}
                <br/> Seuil : ${pal.getSeuil()}
                <br/> Description : ${pal.getDescription()}
                <br/>
            </c:forEach>
        </div>
        <hr/><br/>
    </c:forEach>
    <hr/><br/>

</body>
</html>
