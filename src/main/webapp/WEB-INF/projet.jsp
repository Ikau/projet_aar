<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>">
    <style type="text/css">

        .reponse { display:none; }

    </style>
</head>

<SCRIPT LANGUAGE="JavaScript">

    function afficher(id) {

        document.getElementById(id).style.display = "block";
    }
    function cacher(id) {
        document.getElementById(id).style.display = "none";
    }

</SCRIPT>
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


            <%-- ----------------------------  COMMENTAIRE  ---------------------- --%>
            <c:forEach items="${projet.getMessagesRacines()}" var="message">
            <div class="w3-panel w3-leftbar w3-border-theme">
                <div class="w3-text-gray">
                    Envoyé le ${message.getStringCreation()}. Dernière modification le ${message.getStringModification()}
                </div>
                <p>
                        ${message.getContenu()}
                </p>
                <!-- Bas du commentaire -->
                <div class="w3-container w3-cell w3-text-theme">
                    ${message.getAuteur().getLogin()}
                </div>
                <!-- Partie réponse TODO modifier dynamiquement le bouton repondre -->
                <div class="w3-container w3-cell w3-text-theme">
                    <a href="#" onclick="afficher('r2')">Répondre</a>
                    <div class="reponse" id="r2" >
                        <input class="w3-input" type="text"> <br>
                        <input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="poster"/>
                        </p>
                    </div>
                </div>

                <%-- ------------ REPONSES ------------ --%>
                <c:forEach items="${message.getReponduPar()}" var="reponse">
                <div class="w3-panel w3-leftbar w3-border-theme">
                    <div class="w3-text-gray">
                        Envoyé le ${reponse.getStringCreation()}. Dernière modification le ${reponse.getStringModification()}
                    </div>
                    <p>
                        ${reponse.getContenu()}
                    </p>
                    <!-- Bas du commentaire -->
                    <div class="w3-container w3-cell w3-text-theme">
                        ${reponse.getAuteur().getLogin()}
                    </div>
                    <!-- Partie réponse -->
                    <div class="w3-container w3-cell w3-text-theme">
                        <a href="#" onclick="afficher('r3')">Répondre</a>
                        <div class="reponse" id="r3" >
                            <input class="w3-input" type="text"> <br>
                            <input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="poster"/>
                            </p>
                        </div>
                    </div>
                </div>
                </c:forEach>
                <%-- ---------------------------------- --%>
            </div>
            </c:forEach>
            <%-- ------------------------------------------------------------------ --%>

            <br>
            <h2>Ajouter un commentaire</h2>
            <input class="w3-input" type="text"> <br>
            <input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="poster"/>
        </p>




    </p></div>

    <div class="w3-col" style="width:2%"><p>

    </p></div>

    <div class="w3-col" style="width:30%"><p>
        <div>Somme récoltée : ${projet.getFinancement()} €</div>
        <div class="w3-theme-l4"><div class="w3-theme w3-center w3-padding" style="width:${projet.getPourcentage()}%">${projet.getPourcentage()} %</div></div>

        <div class="w3-display-container" style="height:40px;">
            <div class="w3-padding w3-display-topleft">0€</div>
            <div class="w3-padding w3-display-topright">${projet.getObjectif()} €</div>
        </div>
        <h2>Saisir un Montant :</h2>
        <input class="w3-input" type="text">
        <br>
        <button class="w3-button w3-block w3-theme" type="submit" > Financer </button>

        <c:forEach items="${projet.getPalliers()}" var="pallier">
        <h2>${pallier.getIntitule()}</h2>
        <div class="w3-text-gray">À partir de ${pallier.getSeuil()} €</div>
        <p>${pallier.getDescription()}</p>
        </c:forEach>


        </p></div>

    <div class="w3-col" style="width:4%"><p>

    </p></div>
</div>


</body>
</html>