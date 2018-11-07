<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
    <script language="JavaScript" src="/ressources/js/formulaire-projet.js"></script>
</head>

<body>

<jsp:include page="navbar.jsp"/>

<div class="w3-row">
    <div class="w3-col" style="width:10%"><p>

    </p></div>

    <div class="w3-col" style="width:80%"><p>

        <h1> Formulaire de Lancement de projet</h1>
        <%--@elvariable id="projetWrapper" type="wrappers.ProjetWrapper"--%>
        <form:form class="w3-container" action="/projets/creer" method="post" modelAttribute="projetWrapper">

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
            <label>Date de fin (exclue)</label>
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"/>
            <form:input class="w3-input " type="date" path="dateFin"/>
            <form:errors path="dateFin" cssStyle="color:red;"/>
            <br>


            <label>Catégorie</label>
            <form:errors path="categorieIdList" cssStyle="color:red;" />
            <br>
            <c:forEach items="${categories}" var="c">
                <form:checkbox class="w3-check" value="${c.getId()}" path="categorieIdList"/>
                <label>${c.getIntitule()}</label>
            </c:forEach>

            <br>
            <br>
            <label>Objectif (en €)</label>
            <form:input class="w3-input w3-animate-input" type="number" path="objectif"/>
            <form:errors path="objectif" cssStyle="color:red;"/>

            <br>
            <br>
            <div id="compensation">
                <label>Compensations (en €)</label>
                <form:errors path="pallierList" cssStyle="color:red;" />
                <br>
                <div class="w3-container" id="1">
                    <div class="w3-container w3-cell">Seuil </div>
                    <div class="w3-container w3-cell"><input class="w3-input" type="number" name="pallierList[0].seuil"/></div>
                    <div class="w3-container w3-cell">Intitulé </div>
                    <div class="w3-container w3-cell"><input class="w3-input" type="text" name="pallierList[0].intitule"/></div>
                    <div class="w3-container w3-cell"> Description de la compensation : </div>
                    <div class="w3-container w3-cell" style="width: 50%"><textarea class="w3-input w3-animate-input" type="text" name="pallierList[0].description"> </textarea></div>
                    <br>
                    <br>
                </div>


                <div id="addC" class="w3-display-container">
                    <div class="w3-display-middle"><input  type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="Ajouter une compensation" onclick="ajouter()"/></div>
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