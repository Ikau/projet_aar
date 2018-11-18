<%--
  Created by IntelliJ IDEA.
  User: ikau
  Date: 15/11/2018
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Pour naviguer entre les différentes pages
             Attention on utilise des indexes donc l'affichage se fait en index+1 !--%>
<div class="w3-bar w3-center">
    <c:if test="${indexPage != null}">
        <c:choose>
            <c:when test="${indexPage == 0}">
                <a class="w3-button w3-left">&#10094; Précédent</a>
            </c:when>
            <c:otherwise>
                <a class="w3-button w3-left" onclick="rechercherNumero(${indexPage}, ${categorieActuelle});">
                    &#10094; Précédent
                </a>
            </c:otherwise>
        </c:choose>

        <c:if test="${indexPage > 2}">
            <a class="w3-button" onclick="rechercherNumero(1, ${categorieActuelle});">1</a>
        </c:if>
        <c:if test="${indexPage > 3}">...</c:if>

        <c:forEach items="${numerosGauche}" var="i">
            <a class="w3-button" onclick="rechercherNumero(${i+1}, ${categorieActuelle});">${i+1}</a>
        </c:forEach>

        <a class="w3-button w3-theme-d3">${indexPage+1}</a>

        <c:forEach items="${numerosDroite}" var="i">
            <a class="w3-button" onclick="rechercherNumero(${i+1}, ${categorieActuelle});">${i+1}</a>
        </c:forEach>

        <c:if test="${indexPage < (dernierIndex-3)}">...</c:if>
        <c:if test="${indexPage < (dernierIndex-2)}">
            <a class="w3-button" onclick="rechercherNumero(${dernierIndex+1}, ${categorieActuelle});">${dernierIndex+1}</a>
        </c:if>

        <c:choose>
            <c:when test="${indexPage == dernierIndex}">
                <a class="w3-button w3-right">Suivant &#10095;</a>
            </c:when>
            <c:otherwise>
                <a class="w3-button w3-right" onclick="rechercherNumero(${indexPage+2}, ${categorieActuelle});">
                    Suivant &#10095;
                </a>
            </c:otherwise>
        </c:choose>
        <br/>
        <br/>
    </c:if>
</div>