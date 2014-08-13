<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
    <head>
        <title>Index</title>
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap-theme.min.css" />">
    </head>
    <body class="container-fluid">
        <div class="row">
            <h1 class="col-sm-3 col-md-2 col-lg-1">Index</h1>
            <p class="col-sm-9 col-md-10 col-lg-11">${datetime}</p>
        </div><div class="row">
            <h2 class="col-md-6"><a href="<c:url value='/customers/' />">Customers</a></td></h2>
            <h2 class="col-md-6"><a href="<c:url value='/references/' />">References</a></td></h2>
        </div>
    </body>
</html>