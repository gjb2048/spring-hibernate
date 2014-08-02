<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
    <head>
        <title>Manage Customer</title>
        <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
    </head>
    <body>
        <h1>Manage Customers</h1>

        <c:url var="addAction" value="/customer/add"></c:url>

        <form:form action="${addAction}" commandName="customer">
            <table>
                <c:if test="${!empty customer.name}">
                    <tr>
                        <td><form:label path="id">
                                <spring:message text="ID" />
                            </form:label></td>
                        <td><form:input path="id" readonly="true" size="8"
                                    disabled="true" /> <form:hidden path="id" /></td>
                    </tr>
                </c:if>
                <tr>
                    <td><form:label path="name">
                            <spring:message text="Name" />
                        </form:label></td>
                    <td><form:input path="name" /></td>
                </tr>
                <tr>
                    <td><form:label path="address">
                            <spring:message text="Address" />
                        </form:label></td>
                    <td><form:input path="address" /></td>
                </tr>
                <tr>
                    <td><form:label path="tel">
                            <spring:message text="Tel" />
                        </form:label></td>
                    <td><form:input path="tel" /></td>
                </tr>
                <tr>
                    <td colspan="2"><c:if test="${!empty customer.name}">
                            <input type="submit"
                                   value="<spring:message text="Edit Customer"/>" />
                        </c:if> <c:if test="${empty customer.name}">
                            <input type="submit" value="<spring:message text="Add Customer"/>" />
                        </c:if></td>
                </tr>
            </table>
        </form:form>
        <br>
        <h3>Customer List</h3>
        <table class="tg">
            <tr>
                <th width="80">Customer ID</th>
                <th width="120">Customer Name</th>
                <th width="120">Customer Address</th>
                <th width="120">Customer Tel</th>
                <th width="60">Edit</th>
                <th width="60">Delete</th>
            </tr>
            <c:if test="${!empty listCustomers}">
                <c:forEach items="${listCustomers}" var="customer">
                    <tr>
                        <td>${customer.id}</td>
                        <td>${customer.name}</td>
                        <td>${customer.address}</td>
                        <td>${customer.tel}</td>
                        <td><a href="<c:url value='/customer/edit/${customer.id}' />">Edit</a></td>
                        <td><a
                                href="<c:url value='/customer/remove/${customer.id}' />">Delete</a></td>
                    </tr>
                </c:forEach>
            </c:if>
        </table>

    </body>
</html>