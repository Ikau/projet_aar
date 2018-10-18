<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
</head>

<SCRIPT LANGUAGE="JavaScript">

    function ajouter() {
// pour ajouter une compensation
        var boutton = document.getElementById('addC');
        var newdiv = document.createElement('div');
        newdiv.className = "w3-container w3-cell";
        var text = document.createTextNode('De');
        newdiv.appendChild(text);
        document.getElementById('form1').insertBefore(newdiv,boutton);

        var newdiv2 = document.createElement('div');
        newdiv2.className = "w3-container w3-cell";
        var inp = document.createElement('input');
        inp.type='number';
        inp.className = "w3-input";
        newdiv2.appendChild(inp);
        document.getElementById('form1').insertBefore(newdiv2,boutton);

        var newdiv3 = document.createElement('div');
        newdiv3.className = "w3-container w3-cell";
        var text2 = document.createTextNode('à');
        newdiv3.appendChild(text2);
        document.getElementById('form1').insertBefore(newdiv3,boutton);

        var newdiv4 = document.createElement('div');
        var inp3 = document.createElement('input');
        inp3.type='number';
        inp3.className = "w3-input";
        newdiv4.className = "w3-container w3-cell";
        newdiv4.appendChild(inp3);
        document.getElementById('form1').insertBefore(newdiv4,boutton);

        var newdiv5 = document.createElement('div');
        newdiv5.className = "w3-container w3-cell";
        var text3 = document.createTextNode('Description de la compensation :');
        newdiv5.appendChild(text3);
        document.getElementById('form1').insertBefore(newdiv5,boutton);

        var newdiv6 = document.createElement('div');
        newdiv6.className = "w3-container w3-cell";
        var inp2 = document.createElement('input');
        inp2.type='text';
        inp2.className = "w3-input";
        newdiv6.appendChild(inp2);
        document.getElementById('form1').insertBefore(newdiv6,boutton);


        document.getElementById('form1').insertBefore(document.createElement('br'),boutton);
        document.getElementById('form1').insertBefore(document.createElement('br'),boutton);

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
    <div class="w3-col" style="width:10%"><p>

    </p></div>

    <div class="w3-col" style="width:80%"><p>

        <h1> Formulaire de Lancement de projet</h1>

        <form id="form1" class="w3-container">

            <label>Intitulé</label>
            <input class="w3-input w3-animate-input" type="text">
            <br>
            <label>Résumé</label>
            <input class="w3-input w3-animate-input" type="text">
            <br>
            <label>Description</label>
            <input class="w3-input w3-animate-input" type="text">
            <br>
            <label>Date de fin</label>
            <input class="w3-input w3-animate-input" type="date">
            <br>
            <select class="w3-select w3-border w3-text-theme" name="option">
                <option value="" disabled selected>Choisir la catégorie</option>
                <option value="1">Option 1</option>
                <option value="2">Option 2</option>
                <option value="3">Option 3</option>
            </select>
            <br>
            <br>
            <label>Objectif (en €)</label>
            <input class="w3-input w3-animate-input" type="number">
            <br>
            <br>
        <label>Compensations (en €)</label>
        <br>
            <div class="w3-container w3-cell">De </div>
            <div class="w3-container w3-cell"><input class="w3-input" type="number"></div>
            <div class="w3-container w3-cell"> à </div>
            <div class="w3-container w3-cell"><input class="w3-input" type="number" ></div>
            <div class="w3-container w3-cell"> Description de la compensation : </div>
            <div class="w3-container w3-cell"><input class="w3-input" type="text"></div>
            <br>
            <br>
            <div id="addC" class="w3-display-container">
                <div class="w3-display-middle"><input  type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="Rajouter une compensation" onclick="ajouter()"/></div>
            </div>

            <br>
            <br>
            <br>
            <div class="w3-display-container">
                <div class="w3-display-middle"><input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="Envoyer le Formulaire"/></div>
            </div>

        </form>

        <br>
        <br>

        </p></div>

    <div class="w3-col" style="width:10%"><p>

    </p></div>
</div>


</body>
</html>