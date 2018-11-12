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
</head>
<body>

    <jsp:include page="navbar.jsp"/>

    <%-- TODO Mettre en page --%>
    <%-- ================= Catégorie ================= --%>
    <h2>Catégories</h2>
    <h3>Ajouter une catégorie</h3>
    <%--@elvariable id="categorieTemp" type="modele.Categorie"--%>
    <form:form method="post" modelAttribute="categorieTemp" action="/admin/categories">
        Intitule <form:input type="text" path="intitule"/> <form:errors path="intitule" cssStyle="color:red;"/>
        <input type="submit" value="Créer catégorie"/>
    </form:form>
    <h3>Liste des catégories</h3>
        <table>
            <tr>
                <th>Intitule</th>
                <th></th> <%-- Action --%>
            </tr>
            <c:forEach items="${categories}" var="categeorie">
                <tr id="tr-cat${categeorie.getId()}">
                    <td id="intitule-cat${categeorie.getId()}">${categeorie.getIntitule()}</td>
                    <td id="bouton-cat${categeorie.getId()}">
                        <button onclick="afficherFormCategorie(${categeorie.getId()});">Modifier</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    <hr/>
</body>
</html>
