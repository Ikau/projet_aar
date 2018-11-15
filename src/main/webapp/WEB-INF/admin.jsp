<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ikau
  Date: 12/11/2018
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administration</title>
    <script language="JavaScript" src="/ressources/js/admin.js"></script>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>"/>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>"/>
</head>
<body>

    <jsp:include page="navbar.jsp"/>

    <div class="w3-col" style="width:20%"><p>

    </p></div>

    <div class="w3-col" style="width:60%"><p>




    <%-- ================= Catégorie ================= --%>
    <h2>Catégories</h2>
    <h3>Ajouter une catégorie</h3>
    <%--@elvariable id="categorieTemp" type="modele.Categorie"--%>
        <label>Intitulé</label>

    <form:form method="post" modelAttribute="categorieTemp" action="/admin/categories">
        <form:input class="w3-input" type="text" path="intitule"/> <form:errors path="intitule" cssStyle="color:red;"/>
        <br>
        <input class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="Créer catégorie"/>
    </form:form>
    <h3>Liste des catégories</h3>
        <table class="w3-table w3-bordered">
            <tr>
                <th>Intitule</th>
                <th></th> <%-- Action --%>
            </tr>
            <c:forEach items="${categories}" var="categeorie">
                <tr id="tr-cat${categeorie.getId()}">
                    <td id="intitule-cat${categeorie.getId()}">${categeorie.getIntitule()}</td>
                    <td id="bouton-cat${categeorie.getId()}">
                        <button class="w3-button  w3-hover-theme" onclick="afficherFormCategorie(${categeorie.getId()});">Modifier</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </p></div>
    <div class="w3-col" style="width:20%"><p>

    </p></div>
</body>
</html>
