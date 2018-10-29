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
    <div class="w3-col" style="width:20%"><p>

    </p></div>

    <div class="w3-col" style="width:60%"><p>
         <h1> <i class="fa fa-user-circle"></i> ${courant.getLogin()}</h1>
        <br>
        <h3><i class="fa fa-lock"></i> <a href="/changerlogin">Changer mon mot de passe</a></h3>
        <br>
        <h3><i class="fa fa-pencil"></i> <a href="/changermdp">Changer mon Login</a></h3>
        <br>
        <br>
        <br>
        <div class="w3-container w3-cell">
        <ul class="w3-ul w3-border" >
            <li><h2>Mes projets</h2></li>
            <c:forEach items="${derniersProjetsDeposes}" var="projet">
            <li class="w3-hover-theme"><a href="/projets/${projet.getId()}">${projet.getIntitule()}</a></li>
            </c:forEach>
        </ul>
        </div>
            <div class="w3-container w3-cell">
            <ul class="w3-ul" >
                <li><h2>Modifier mes projets</h2></li>
                <li><a href="#" > Modifier p1</a></li>
                <li><a href="#" > Modifier p2</a></li>
                <li><a href="#" > Modifier p3</a></li>
            </ul>
        </div>
            <div class="w3-container w3-cell">
        <ul class="w3-ul w3-border" >
            <li><h2>Mes financements</h2></li>
            <li class="w3-hover-theme">Titre p1</li>
            <li class="w3-hover-theme">Titre p2</li>
            <li class="w3-hover-theme">Titre p3</li>
        </ul>
        </div>
    </p></div>

    <div class="w3-col" style="width:20%"><p>

    </p></div>
</div>


</body>