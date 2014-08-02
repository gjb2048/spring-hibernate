<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
    <head>
        <title>Index</title>
    </head>
    <body>
        <h1>Index</h1>
        <p>${datetime}</p>
        <p><a href="<c:url value='/customers/' />">Customers</a></td></p>
    </body>
</html>