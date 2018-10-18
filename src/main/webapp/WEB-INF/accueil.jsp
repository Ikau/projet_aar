<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
</head>
<body>

<!--- Barre de navigation -->
<% if (session.getAttribute("courant") != null) { %>
<div class="w3-bar w3-theme-dark">
    <a href="/" class="w3-bar-item w3-button">Home</a>
    <a href="/prof" class="w3-bar-item w3-button">Mon profil</a>
    <a href="/form" class="w3-bar-item w3-button">Lancer un projet</a>
    <a href="/deco" class="w3-bar-item w3-button">Se déconnecter</a>
</div>
<% } else { %>
<div class="w3-bar w3-theme-dark">
    <a href="/" class="w3-bar-item w3-button">Home</a>
    <a href="/co" class="w3-bar-item w3-button">Se connecter</a>
    <a href="/insc" class="w3-bar-item w3-button">S'inscrire</a>
</div>
<% } %>


<div class="w3-row">
    <div class="w3-col" style="width:20%"><p>

    </p></div>

    <div class="w3-col" style="width:60%"><p>
        <!-- Recherche par catégorie -->
        <select class="w3-select w3-border w3-text-theme" name="option">
            <option value="" disabled selected>Rechercher par catégorie</option>
            <option value="1">Option 1</option>
            <option value="2">Option 2</option>
            <option value="3">Option 3</option>
        </select>

        <!-- Les 3 derniers projets -->

        <c:if test="${projet.size() >= 1}">
        <div class="w3-card-4">
            <header class="w3-container w3-theme-d3 ">
                <h1>${projet.get(0).getIntitule()}</h1>
            </header>
            <div class="w3-container">
                <p>
                    ${projet.get(0).getResume()}
                </p>
            </div>
            <footer class="w3-container">
                    <div class="w3-row">
                        <div class="w3-col s9 w3-center"><p>
                            <div class="w3-theme-l4"><div class="w3-theme w3-center w3-padding" style="width:75%">Avancement du financement</div></div>
                            </p></div>
                        <div class="w3-col s3 w3-center"><p>
                            <a href="/pro"><input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" value="Voir le projet"/></a>
                        </p></div>
                    </div>
             </footer>
        </div>
        </c:if>
        <br>
        <c:if test="${projet.size() >= 2 }">
        <div class="w3-card-4">
            <header class="w3-container w3-theme-d3 ">
                <h1>${projet.get(1).getIntitule()}</h1>
            </header>
            <div class="w3-container">
                <p>
                    ${projet.get(1).getResume()}
                </p>
            </div>
            <footer class="w3-container">
                <div class="w3-row">
                    <div class="w3-col s9 w3-center"><p>
                        <div class="w3-theme-l4"><div class="w3-theme w3-center w3-padding" style="width:75%">Avancement du financement</div></div>
                        </p></div>
                    <div class="w3-col s3 w3-center"><p>
                        <a href="/pro"><input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" value="Voir le projet"/></a>
                    </p></div>
                </div>
            </footer>
        </div>
        </c:if>
        <br>
        <c:if test="${projet.size() >= 3}">
        <div class="w3-card-4">
            <header class="w3-container w3-theme-d3 ">
                <h1>${projet.get(2).getIntitule()}</h1>
            </header>
            <div class="w3-container">
                <p>
                    ${projet.get(2).getResume()}
                </p>
            </div>
            <footer class="w3-container">
                <div class="w3-row">
                    <div class="w3-col s9 w3-center"><p>
                        <div class="w3-theme-l4"><div class="w3-theme w3-center w3-padding" style="width:75%">Avancement du financement</div></div>
                        </p></div>
                    <div class="w3-col s3 w3-center"><p>
                        <a href="/pro"><input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" value="Voir le projet"/></a>
                    </p></div>
                </div>
            </footer>
        </div>
        </c:if>

    </p></div>
    <div class="w3-col" style="width:20%"><p>

    </p></div>
</div>


</body>
</html>