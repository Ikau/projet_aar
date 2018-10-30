<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
</head>

<SCRIPT LANGUAGE="JavaScript">

    var com = 1;

    function ajouter() {

// pour ajouter une compensation
        var bigDiv = document.createElement('div');
        bigDiv.className = "w3-container";
        var id = com;
        com = com +1;
        bigDiv.id = com;

        var boutton = document.getElementById('addC');
        var newdiv = document.createElement('div');
        newdiv.className = "w3-container w3-cell";
        var text = document.createTextNode('Seuil');
        newdiv.appendChild(text);
        bigDiv.appendChild(newdiv)
        //document.getElementById('form1').insertBefore(newdiv,boutton);

        var newdiv2 = document.createElement('div');
        newdiv2.className = "w3-container w3-cell";
        var inp = document.createElement('form:input');
        inp.type='number';
        inp.className = "w3-input";
        inp.path="palliers["+id+"].seuil";
        newdiv2.appendChild(inp);
        bigDiv.appendChild(newdiv2)
        //document.getElementById('form1').insertBefore(newdiv2,boutton);

        var newdiv3 = document.createElement('div');
        newdiv3.className = "w3-container w3-cell";
        var text2 = document.createTextNode('Intitulé');
        newdiv3.appendChild(text2);
        bigDiv.appendChild(newdiv3)
        //document.getElementById('form1').insertBefore(newdiv3,boutton);

        var newdiv4 = document.createElement('div');
        var inp3 = document.createElement('form:input');
        inp3.type='text';
        inp3.className = "w3-input";
        inp3.path="palliers["+id+"].intitule";
        newdiv4.className = "w3-container w3-cell";
        newdiv4.appendChild(inp3);
        bigDiv.appendChild(newdiv4)
        //document.getElementById('form1').insertBefore(newdiv4,boutton);

        var newdiv5 = document.createElement('div');
        newdiv5.className = "w3-container w3-cell";
        var text3 = document.createTextNode('Description de la compensation :');
        newdiv5.appendChild(text3);
        bigDiv.appendChild(newdiv5)
        //document.getElementById('form1').insertBefore(newdiv5,boutton);

        var newdiv6 = document.createElement('div');
        newdiv6.className = "w3-container w3-cell";
        var inp2 = document.createElement('form:textarea');
        inp2.type='text';
        inp2.className = "w3-input";
        inp2.name="palliers["+id+"].description";
        newdiv6.style="width: 50%";
        newdiv6.appendChild(inp2);
        bigDiv.appendChild(newdiv6);
        bigDiv.appendChild(document.createElement('br'));
        bigDiv.appendChild(document.createElement('br'));
        document.getElementById('compensation').insertBefore(bigDiv,boutton);

    }

    function enlever(){
        var div = document.getElementById(com);
        document.getElementById('compensation').removeChild(div);
        com =com-1;

    }


</SCRIPT>
<body>

<!--- Barre de navigation -->
<% if (session.getAttribute("auth") != null) { %>
<div class="w3-bar w3-theme-dark">
    <a href="/" class="w3-bar-item w3-button">Home</a>
    <a href="/profil" class="w3-bar-item w3-button">Mon profil</a>
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
    <div class="w3-col" style="width:10%"><p>

    </p></div>

    <div class="w3-col" style="width:80%"><p>

        <h1> Formulaire de Lancement de projet</h1>
        <%--@elvariable id="projetTemp" type="modele.Projet"--%>
        <form:form class="w3-container" action="/ajoutProjet" method="post" modelAttribute="projetTemp">

            <label>Intitulé</label>
            <form:input class="w3-input" type="text" path="intitule"/>
            <form:errors path="intitule" cssStyle="color:red;"/>
            <br>
            <label>Résumé</label>
            <form:textarea class="w3-input" type="text" path="resume"></form:textarea>
            <form:errors path="resume" cssStyle="color:red;"/>
            <br>
            <label>Description</label>
            <form:textarea class="w3-input" type="text" path="description"></form:textarea>
            <form:errors path="description" cssStyle="color:red;"/>

            <br>
            <label>Date de fin</label>
            <fmt:formatDate
            <form:input class="w3-input " type="date" path="dateFin"/>
            <br>


            <label>Catégorie</label>
            <br>
            <c:forEach items="${categories}" var="c">
                <form:checkbox class="w3-check" value="${c.getId()}" path="categories"/>
                <label>${c.getIntitule()}</label>
            </c:forEach>



            <br>
            <br>
            <label>Objectif (en €)</label>
            <form:input class="w3-input w3-animate-input" type="number" path="objectif"/>

            <br>
            <br>
            <div id="compensation">
                <label>Compensations (en €)</label>
                <br>
                <div class="w3-container" id="1">
                    <div class="w3-container w3-cell">Seuil </div>
                    <div class="w3-container w3-cell"><input class="w3-input" type="number" name="compensationBas1"/></div>
                    <div class="w3-container w3-cell">Intitulé </div>
                    <div class="w3-container w3-cell"><input class="w3-input" type="text" name="compensationHaut1" /></div>
                    <div class="w3-container w3-cell"> Description de la compensation : </div>
                    <div class="w3-container w3-cell" style="width: 50%"><textarea class="w3-input w3-animate-input" type="text" name="compensationDesc1"> </textarea></div>
                    <br>
                    <br>
                </div>


                <div id="addC" class="w3-display-container">
                    <div class="w3-display-middle"><input  type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="Rajouter une compensation" onclick="ajouter()"/></div>
                </div>
                <br>
                <br>
                <div class="w3-display-container">
                    <div class="w3-display-middle"><input  type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="Enlever une compensation" onclick="enlever()"/></div>
                </div>

            </div>

            <br>
            <br>
            <br>
            <div class="w3-display-container">
                <div class="w3-display-middle"><input class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="Envoyer le Formulaire"/></div>
            </div>

        </form:form>

        <br>
        <br>

        </p></div>

    <div class="w3-col" style="width:10%"><p>

    </p></div>
</div>


</body>
</html>