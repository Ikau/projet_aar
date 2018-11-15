<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ikau
  Date: 09/11/2018
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
    <title>Tous mes projets</title>
</head>
<body>
    <jsp:include page="navbar.jsp"/>
    <div class="w3-col" style="width:20%"><p>

    </p></div>

    <div class="w3-col" style="width:80%"><p>
        <div class="w3-container w3-responsive" >
        <h2>Mes projets déposés</h2>
            <table class="w3-table-all" style="width:70%">
                <tr class="w3-theme-d3">
                    <th>Intitule</th>
                    <th>Avancement du financement</th>
                    <th>Temps restant</th>
                    <th><%-- Vide car ça va être le bouton de modification. --%></th>
                </tr>
                <c:forEach items="${projets}" var="projet">
                    <tr>
                        <td><a href="/projets/${projet.getId()}">${projet.getIntitule()}</a></td>
                        <td>${projet.getFinancement()} € / ${projet.getObjectif()} € (${projet.getPourcentage()} %)</td>
                        <td>${projet.getTempsRestant()}</td>
                        <td><a href="/profil/projets/${projet.getId()}" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme">Modifier</a></td>
                    </tr>
                </c:forEach>
            </table>


        </div>
        </p></div>

    <div class="w3-col" style="width:20%"><p>

    </p></div>


</body>
</html>
