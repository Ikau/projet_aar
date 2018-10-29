<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
</head>
<body>

<!--- Barre de navigation -->
<% if (session.getAttribute("auth") != null) { %>
<div class="w3-bar w3-theme-dark">
    <a href="/" class="w3-bar-item w3-button">Home</a>
    <a href="/profils/${courant.getId()}" class="w3-bar-item w3-button">Mon profil</a>
    <a href="/form" class="w3-bar-item w3-button">Lancer un projet</a>
    <a href="/deconnexion" class="w3-bar-item w3-button">Se déconnecter</a>
</div>
<% } else { %>
<div class="w3-bar w3-theme-dark">
    <a href="/" class="w3-bar-item w3-button">Home</a>
    <a href="/connexion" class="w3-bar-item w3-button">Se connecter</a>
    <a href="/inscription" class="w3-bar-item w3-button">S'inscrire</a>
</div>
<% } %>


<div class="w3-row">
    <div class="w3-col" style="width:25%"><p>
    </p></div>

    <div class="w3-col" style="width:50%"><p>
        <%--@elvariable id="utilisateurTemp" type="modele.Utilisateur"--%>
        <form:form class="w3-container" action="/profils/${courant.getId()}/login" method="post" modelAttribute="utilisateurTemp">

        <label> Changer de Login</label>
            <form:input class="w3-input" type="text" path="login"/>

            <form:errors path="login" cssStyle="color:red;" />
        <br>
        <br>
        <div class="w3-display-container">
            <div class="w3-display-middle"><input class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="envoyer"/></div>
        </div>
        </form:form>


        </p></div>


    <div class="w3-col" style="width:25%"><p>
    </p></div>
</div>


</body>
</html>