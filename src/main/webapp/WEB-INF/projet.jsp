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
    <div class="w3-col" style="width:4%"><p>

    </p></div>

    <div class="w3-col" style="width:60%"><p>
        <h1> Titre Projet </h1>
        <h4> <i class="fa fa-user-circle"></i> Porteur du projet </h4>
        <div class="w3-container w3-cell w3-text-grey"> <i class="fa fa-hourglass-start"></i> date de lancement </div>
        <div class="w3-container w3-cell w3-text-grey"> <i class="fa fa-hourglass-half"></i> temps restant </div>
        <div class="w3-container w3-cell w3-text-grey"> <i class="fa fa-hourglass-3"></i> date de fin </div>
        <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi faucibus massa quis dui vulputate euismod. Proin sit amet orci viverra, finibus leo ac, sagittis enim. In congue dui mauris, eget rhoncus turpis lacinia quis. Sed tincidunt tincidunt purus, eu facilisis tortor sollicitudin eget. Aenean aliquam mauris est, vel venenatis mauris porttitor quis. In euismod ligula eget scelerisque suscipit. Vestibulum hendrerit tristique leo, et pretium nisi ultrices sed. Aenean ac posuere justo. Morbi mattis justo eu maximus commodo. Donec quam sapien, eleifend quis felis a, consequat molestie orci. Integer eros erat, blandit in nibh sed, convallis faucibus sem. Fusce id porttitor sapien. Vivamus iaculis urna vel ex varius, et sodales orci rutrum. Cras sit amet porttitor lacus.
        </p>

        <p>
            <h1> Commentaires </h1>
        <!-- -------------------UN COMMENTAIRE--------------------------------------- -->
            <div class="w3-panel w3-leftbar w3-border-theme">
                <div class="w3-text-gray">
                    date du commentaire
                </div>
                <p>Donec semper maximus quam, ultrices placerat sapien aliquet in. Aliquam in vestibulum quam, eget ultricies nulla. Sed auctor est ipsum, posuere facilisis mauris ultricies feugiat. Nulla vulputate ac mi quis varius.</p>
                <!-- Bas du commentaire -->
                <div class="w3-container w3-cell w3-text-theme">
                   auteur
                </div>
                <!-- Partie réponse -->
                <div class="w3-container w3-cell w3-text-theme">
                    <a href="#" onclick="afficher('r1')">Répondre</a>
                    <div class="reponse" id="r1" >
                        <input class="w3-input" type="text"> <br>
                        <input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="poster"/>
                        </p>
                    </div>
                </div>
            </div>
        <!-- -------------------------------------------------------------------- -->


        <div class="w3-panel w3-leftbar w3-border-theme">
            <div class="w3-text-gray">
                date du commentaire
            </div>
            <p>Donec semper maximus quam, ultrices placerat sapien aliquet in. Aliquam in vestibulum quam, eget ultricies nulla. Sed auctor est ipsum, posuere facilisis mauris ultricies feugiat. Nulla vulputate ac mi quis varius.</p>
            <!-- Bas du commentaire -->
            <div class="w3-container w3-cell w3-text-theme">
                auteur
            </div>
            <!-- Partie réponse -->
            <div class="w3-container w3-cell w3-text-theme">
                <a href="#" onclick="afficher('r2')">Répondre</a>
                <div class="reponse" id="r2" >
                    <input class="w3-input" type="text"> <br>
                    <input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="poster"/>
                    </p>
                </div>
            </div>
            <div class="w3-panel w3-leftbar w3-border-theme">
                <div class="w3-text-gray">
                    date du commentaire
                </div>
                <p>Donec semper maximus quam, ultrices placerat sapien aliquet in. Aliquam in vestibulum quam, eget ultricies nulla. Sed auctor est ipsum, posuere facilisis mauris ultricies feugiat. Nulla vulputate ac mi quis varius.</p>
                <!-- Bas du commentaire -->
                <div class="w3-container w3-cell w3-text-theme">
                    auteur
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
        </div>

        <br>
        <h2>Ajouter un commentaire</h2>
        <input class="w3-input" type="text"> <br>
        <input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="poster"/>
        </p>




    </p></div>

    <div class="w3-col" style="width:2%"><p>

    </p></div>

    <div class="w3-col" style="width:30%"><p>
        <div class="w3-theme-l4"><div class="w3-theme w3-center w3-padding" style="width:75%">Avancement du financement</div></div>

        <div class="w3-display-container" style="height:40px;">
            <div class="w3-padding w3-display-topleft">0€</div>
            <div class="w3-padding w3-display-topright">MAX €</div>
        </div>
        <h2>Saisir un Montant :</h2>
        <input class="w3-input" type="text">
        <br>
        <button class="w3-button w3-block w3-theme" type="submit" > Financer </button>

        <h2>De 0€ à 10€</h2>
        <p>Donec semper maximus quam, ultrices placerat sapien aliquet in. Aliquam in vestibulum quam, eget ultricies nulla. Sed auctor est ipsum, posuere facilisis mauris ultricies feugiat. Nulla vulputate ac mi quis varius.</p>

        <h2>De 10€ à 20€</h2>
        <p>Donec semper maximus quam, ultrices placerat sapien aliquet in. Aliquam in vestibulum quam, eget ultricies nulla. Sed auctor est ipsum, posuere facilisis mauris ultricies feugiat. Nulla vulputate ac mi quis varius.</p>


        </p></div>

    <div class="w3-col" style="width:4%"><p>

    </p></div>
</div>


</body>
</html>