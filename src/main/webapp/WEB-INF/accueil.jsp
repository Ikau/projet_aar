<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Accueil</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>"/>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>"/>
    <link rel="stylesheet" href="/ressources/css/accueil.css"/>
    <script language="JavaScript" src="/ressources/js/accueil.js"></script>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="w3-row">
    <div class="w3-col" style="width:20%"><p>

    </p></div>

    <div class="w3-col" style="width:60%"><p>
        <%-- Recherche par catégorie --%>
        <form name='cat' action="/recherche" method="get">
        <input hidden name="page" value="1"/>
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

        <%-- Pour naviguer entre les différentes pages --%>
        <%-- TODO Il faudrait mettre en forme la div de navigation --%>
        <div>
            <c:if test="${indexPage != null}">
                <c:choose>
                    <c:when test="${indexPage == 0}">
                        <a>
                            <
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a class="fleches-pages" onclick="rechercherFleche(${indexPage}, ${categorieActuelle});">
                            <
                        </a>
                    </c:otherwise>
                </c:choose>

                Page <input id="inputPage1" type="text" value="${indexPage+1}" onkeyup='rechercherInput(event, "inputPage1", ${categorieActuelle});'/>

                <c:choose>
                    <c:when test="${estDernierePage}">
                        <a>
                            >
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a class="fleches-pages" onclick="rechercherFleche(${indexPage+2}, ${categorieActuelle});">
                            >
                        </a>
                    </c:otherwise>
                </c:choose>
                <br/>
                <br/>
            </c:if>
        </div>

        <%-- Les 3 derniers projets OU le résultat de la recherche --%>
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

        <%-- Pour naviguer entre les différentes pages --%>
        <%-- TODO Il faudrait mettre en forme la div de navigation --%>
        <div>
            <c:if test="${indexPage != null}">
                <c:choose>
                    <c:when test="${indexPage == 0}">
                        <a>
                            <
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a class="fleches-pages" onclick="rechercherFleche(${indexPage}, ${categorieActuelle});">
                            <
                        </a>
                    </c:otherwise>
                </c:choose>

                Page <input id="inputPage2" type="text" value="${indexPage+1}" onkeyup='rechercherInput(event, "inputPage2", ${categorieActuelle});'/>

                <c:choose>
                    <c:when test="${estDernierePage}">
                        <a>
                            >
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a class="fleches-pages" onclick="rechercherFleche(${indexPage+2}, ${categorieActuelle});">
                            >
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </p></div>
    <div class="w3-col" style="width:20%"><p></p></div>
</div>


</body>
</html>