<%-- 
    Document   : error
    Created on : 23-Aug-2014, 15:02:45
    Author     : Gareth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap-theme.min.css" />">
    </head>
    <body>
        <h1>This is an error not a bug</h1>
        <p>Application has encountered Anne Error on ${url}.  She is very disappointed with you.</p>

        <!--
        Failed URL: ${url}
        Exception:  ${exceptionMessage}
        <c:forEach items="${exception.stackTrace}" var="ste">    ${ste} 
        </c:forEach>
        <c:if test="${not empty cause}">
        Cause: ${cause.message}
            <c:forEach items="${cause.stackTrace}" var="stc">    ${stc} 
            </c:forEach>
        </c:if>
        -->        
    </body>
</html>
