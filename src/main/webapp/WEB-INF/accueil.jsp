<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Accueil</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="w3-row">
    <div class="w3-col" style="width:20%"><p>

    </p></div>

    <div class="w3-col" style="width:60%"><p>
        <%-- Recherche par catégorie --%>
        <form name='cat' action="/" method="post">
        <select class="w3-select w3-border w3-text-theme" name="option" onchange="this.form.submit();">
            <c:choose>
                <c:when test="${empty categorieChoisie}">
                    <option value="" disabled selected>Rechercher par catégorie</option>
                </c:when>
                <c:otherwise>
                    <option value="" disabled selected>${categorieChoisie.getIntitule()}</option>
                </c:otherwise>
            </c:choose>

            <c:forEach items="${categories}" var="c">
                <option value="${c.getId()}">${c.getIntitule()}</option>
            </c:forEach>

        </select>
        </form>

        <%-- Les 3 derniers projets --%>
        <c:forEach items="${projets}" var="p">
            <div class="w3-card-4">
                <header class="w3-container w3-theme-d3 ">
                    <h1>${p.getIntitule()}</h1>
                </header>
                <div class="w3-container">
                    <p>${p.getResume()}</p>
                </div>
                <footer class="w3-container">
                    <div class="w3-row">
                        <div class="w3-col s9 w3-center">
                            <p><div class="w3-theme-l4">
                                    <div class="w3-theme w3-center w3-padding" style="max-width:100%;width:${p.getPourcentage()}%">${p.getPourcentage()} %</div>
                            </div></p>
                        </div>
                        <div class="w3-col s3 w3-center"><p>
                            <a href="/projets/${p.getId()}"><input type="button" class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" value="Voir le projet"/></a>
                        </p></div>
                    </div>
                 </footer>
            </div>
            <br>
        </c:forEach>

    </p></div>
    <div class="w3-col" style="width:20%"><p>

    </p></div>
</div>


</body>
</html>