<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>${projet.getIntitule()}</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="/ressources/css/projet.css">
    <script language="JavaScript" src="/ressources/js/projet.js"></script>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="w3-row">
    <div class="w3-col" style="width:4%"><p>

    </p></div>

    <div class="w3-col" style="width:60%"><p>
        <h1>${projet.getIntitule()}</h1>
        <p>${projet.getResume()}</p>
        <h4> <i class="fa fa-user-circle"></i> ${projet.getPorteur().getLogin()}</h4>
        <div class="w3-container w3-cell w3-text-grey"> <i class="fa fa-hourglass-start"></i> ${projet.getStringDepot()}</div>
        <div class="w3-container w3-cell w3-text-grey"> <i class="fa fa-hourglass-half"></i> ${projet.getTempsRestant()}</div>
        <div class="w3-container w3-cell w3-text-grey"> <i class="fa fa-hourglass-3"></i> ${projet.getStringFin()}</div>

        <p>${projet.getDescription()}</p>
        <p>
            <h1> Commentaires </h1>
            <%-- --------------------------  COMMENTAIRE  ------------------------- --%>
            <c:forEach items="${projet.getMessagesRacines()}" var="message">
                <c:set var="message" scope="request" value="${message}"/>
                <jsp:include page="message.jsp"/>
            </c:forEach>
            <%-- ------------------------------------------------------------------ --%>

            <br>
            <h2>Ajouter un commentaire</h2>
            <c:choose>
                <c:when test="${auth != null}">
                    <%--@elvariable id="messageTemp" type="modele.Message"--%>
                    <form:form action="/projets/${projet.getId()}/messages" method="post" modelAttribute="messageTemp">
                        <form:textarea path="contenu" class="w3-input" type="text"/> <br>
                        <form:errors path="contenu" cssStyle="color:red;"/>
                        <input class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="Envoyer"/>
                    </form:form>
                </c:when>
                <c:otherwise>
                    <div>
                        <a href="/connexion">Se connecter</a> ou <a href="/inscription">s'inscrire</a> pour commenter le projet.
                    </div>
                </c:otherwise>
            </c:choose>
        </p>
    </p></div>

    <div class="w3-col" style="width:2%"><p></p></div>

    <div class="w3-col" style="width:30%"><p>
        <div>Somme récoltée : ${projet.getFinancement()} €</div>
        <div class="w3-theme-l4"><div class="w3-theme w3-center w3-padding" style="max-width:100%;width:${projet.getPourcentage()}%">${projet.getPourcentage()} %</div></div>

        <div class="w3-display-container" style="height:40px;">
            <div class="w3-padding w3-display-topleft">0€</div>
            <div class="w3-padding w3-display-topright">${projet.getObjectif()} €</div>
        </div>

        <c:choose>
        <c:when test="${auth != null}">
            <div>
                <div>Votre participation : ${participation} €</div>
                <div>Votre pallier : ${projet.getPallierFinancement(participation)}</div>
            </div>

            <h2>Saisir un Montant :</h2>
            <%--@elvariable id="donTemp" type="modele.Don"--%>
            <form:form method="post" action="/projets/${projet.getId()}/financer" modelAttribute="donTemp">
                <form:input class="w3-input" type="number" path="montant"/>
                <form:errors cssStyle="color:red;" path="montant"/>
                <br>
                <input class="w3-button w3-block w3-theme" type="submit" value="Financer"/>
            </form:form>
        </c:when>
        <c:otherwise>
            <a href="/connexion">Se connecter</a> ou <a href="/inscription">s'inscrire</a> pour financer le projet.
        </c:otherwise>
        </c:choose>

        <c:forEach items="${projet.getPalliers()}" var="pallier">
        <h2>${pallier.getIntitule()}</h2>
        <div class="w3-text-gray">À partir de ${pallier.getSeuil()} €</div>
        <p>${pallier.getDescription()}</p>
        </c:forEach>
    </p></div>

    <div class="w3-col" style="width:4%"><p></p></div>
</div>
</body>
</html>