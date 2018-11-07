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
    <p>
            ${message.getContenu()}
    </p>
    <%-- Bas du commentaire --%>
    <div class="w3-container w3-cell w3-text-theme">
            ${message.getAuteur().getLogin()}
    </div>
    <div class="w3-container w3-cell w3-text-theme" style="width: 100%">
        <c:choose>
        <c:when test="${auth != null}">
        <a href="#" onclick="afficher('${message.getId()}')">Répondre</a>
        <div class="reponse" id="${message.getId()}" >
            <%--@elvariable id="messageTemp" type="modele.Message"--%>
            <form:form action="/projets/${projet.getId()}/messages/${message.getId()}" method="post" modelAttribute="messageTemp">
                <form:textarea path="contenu" class="w3-input" type="text"/> <br>
                <input class="w3-button w3-white w3-border w3-border-theme w3-hover-theme" type="submit" value="Envoyer"/>
            </form:form>
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