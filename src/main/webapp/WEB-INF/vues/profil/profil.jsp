<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>">
</head>
<body>

<jsp:include page="../../inclusions/navbar.jsp"/>

<div class="w3-row">
    <div class="w3-col" style="width:20%"><p>

    </p></div>

    <div class="w3-col" style="width:60%"><p>
         <h1> <i class="fa fa-user-circle"></i> ${courant.getLogin()}</h1>
        <br>
        <h3><i class="fa fa-pencil"></i> <a href="/profil/login">Changer mon Login</a></h3>
        <br>
        <h3><i class="fa fa-lock"></i> <a href="/profil/motdepasse">Changer mon mot de passe</a></h3>
        <br>
        <br>
        <br>
        <div class="w3-container w3-cell">
            <table class="w3-table w3-border w3-bordered">
                <tr>
                    <th><h2>Mes derniers projets</h2></th>
                    <th></th>
                </tr>

                    <c:forEach items="${derniersProjetsDeposes}" var="projet">
                        <tr>
                        <td class="w3-hover-theme"><a href="/projets/${projet.getId()}">${projet.getIntitule()}</a></td>
                        <c:choose>
                            <c:when test="${projet.estTermine()}"><td>(TERMINÉ)</td></c:when>
                            <c:otherwise>
                                <td class="w3-hover-theme"><a href="/profil/projets/${projet.getId()}">modifier</a></td>
                            </c:otherwise>
                        </c:choose>
                        </tr>
                    </c:forEach>
                <tr>
                    <td class="w3-hover-theme"><a href="/profil/projets">Voir tous mes projets</a></td>
                </tr>
            </table>

        </div>
        <div class="w3-container w3-cell">
        </div>

        <div class="w3-container w3-cell">
            <ul class="w3-ul w3-border" >
                <li><h2>Mes derniers financements</h2></li>
                <c:forEach items="${derniersFinancements}" var="don">
                    <li class="w3-hover-theme">
                    <c:choose>
                        <c:when test="${don.isActif()}">
                            ${don.getMontant()} € (<a href="/projets/${don.getProjetSoutenu().getId()}">${don.getProjetSoutenu().getIntitule()}</a>)
                        </c:when>
                        <c:otherwise>
                            (ANNULÉ) ${don.getMontant()} € (<a href="/projets/${don.getProjetSoutenu().getId()}">${don.getProjetSoutenu().getIntitule()}</a>)
                        </c:otherwise>
                    </c:choose>
                    </li>
                </c:forEach>
                <li class="w3-hover-theme"><a href="/profil/financements">Voir tous mes financements</a></li>
            </ul>
        </div>
    </p></div>

    <div class="w3-col" style="width:20%"><p>

    </p></div>
</div>


</body>