<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>">
    <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/lib/w3-theme-indigo.css"/>">
</head>
<body>
<jsp:include page="../inclusions/navbar.jsp"/>
<div class="w3-display-container w3-theme" style="height:500px;">

    <div class="w3-padding w3-display-middle"><h1>${errorMsg}</h1></div>

</div>

</body>
</html>