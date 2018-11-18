<%--
  Created by IntelliJ IDEA.
  User: ikau
  Date: 06/11/2018
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="w3-panel w3-leftbar w3-border-theme">
    <%-- Entête du commentaire --%>
    <div class="w3-text-gray">
        Envoyé le ${message.getStringCreation()}. Dernière modification le ${message.getStringModification()}
    </div>
    <%-- Corps du commentaire --%>
    <p id="msg${message.getId()}">
        <c:choose>
            <c:when test="${message.isActif()}">
                ${message.getContenu()}
            </c:when>
            <c:otherwise>
                [Message supprimé]
            </c:otherwise>
        </c:choose>
    </p>
    <%-- Bas du commentaire --%>
    <div class="w3-container w3-cell w3-text-theme">
        <c:if test="${message.isActif()}">
            ${message.getAuteur().getLogin()}
        </c:if>
    </div>
    <div id="div${message.getId()}" class="w3-container w3-cell w3-text-theme" style="width: 100%">
        <c:choose>
        <c:when test="${auth != null}">
            <div id="a${message.getId()}">
                <c:if test="${message.getAuteur().getId() == courant.getId() && message.isActif()}">
                    <a class="action-message" onclick="suppression(${message.getId()})">Supprimer</a>
                    <a class="action-message" onclick="afficherForm(${message.getId()}, ${projet.getId()}, 0)">Éditer</a>
                </c:if>
                <a class="action-message" onclick="afficherForm(${message.getId()}, ${projet.getId()}, 1)">Répondre</a>
            </div>
        </c:when>
        <c:otherwise>
            <div>
                <a href="/connexion">Se connecter</a> ou <a href="/inscription">s'inscrire</a> pour répondre.
            </div>
        </c:otherwise>
        </c:choose>
    </div>

    <%-- ------------ REPONSES ------------ --%>
    <c:forEach items="${message.getReponduPar()}" var="message">
        <c:set var="message" scope="request" value="${message}"/>
        <jsp:include page="message.jsp"/>
    </c:forEach>
    <%-- ---------------------------------- --%>
</div>