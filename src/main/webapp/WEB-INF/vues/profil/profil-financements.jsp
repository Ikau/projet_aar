<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ikau
  Date: 08/11/2018
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
    <script language="JavaScript" src="/ressources/js/profil-financements.js"></script>
    <title>Mes financements</title>
</head>
<body>
    <jsp:include page="../../inclusions/navbar.jsp"/>
    <div class="w3-col" style="width:20%"><p>

    </p></div>

    <div class="w3-col" style="width:80%"><p>
        <div class="w3-container w3-responsive" >
        <h2>Tous mes financements</h2>
        <table class="w3-table-all" style="width:70%">
            <tr class="w3-theme-d3">
                <th>Intitule</th>
                <th>Montant</th>
                <th></th> <%-- vide car on va mettre les 'annuler' --%>
            </tr>
            <c:forEach items="${financements}" var="don">
                <c:set value="${don.getProjetSoutenu()}" var="projet"/>
                <tr>
                    <td><a href="/projets/${projet.getId()}">${projet.getIntitule()}</a></td>

                    <c:choose>
                        <c:when test="${don.isActif()}">
                            <td>${don.getMontant()}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${!don.getProjetSoutenu().estTermine()}">
                                        <form onsubmit="return confirmer();" method="post" action="/financements/${don.getId()}">
                                            <input type="submit" value="Annuler" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme"/>
                                        </form>
                                    </c:when>
                                    <c:otherwise>(TERMINÉ)</c:otherwise>
                                </c:choose>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>Annulé le ${don.getStringModification()} (${don.getMontant()} €).</td>
                            <td></td> <%-- Vide car il n'y a rien à annuler --%>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </div>
    </p></div>

    <div class="w3-col" style="width:20%"><p>

    </p></div>
</body>
</html>
