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
            <h1 class="col-sm-3 col-md-3 col-lg-3 wordbreak">References</h1>
            <div class="col-sm-9 col-md-9 col-lg-9">
                <table>
                    <tr class="row">
                        <td class="col-sm-3 col-md-3 col-lg-3"><p>Source</p></td>
                        <td class="col-sm-9 col-md-9 col-lg-9"><a href="//github.com/gjb2048/spring-hibernate" target="_blank">GitHub repository</a></td>
                    </tr>
                    <tr class="row">
                        <td class="col-sm-3 col-md-3 col-lg-3"><p>Original article</p></td>
                        <td class="col-sm-9 col-md-9 col-lg-9"><a href="//malalanayake.wordpress.com/2014/07/27/spring-mvc-with-spring-hibernate/" target="_blank">Spring MVC with Hibernate</a></td>
                    </tr>
                    <tr class="row">
                        <td class="col-sm-3 col-md-3 col-lg-3"><p>Java config</p></td>
                        <td class="col-sm-9 col-md-9 col-lg-9"><a href="//krams915.blogspot.co.uk/2012/12/spring-and-thymeleaf-with-javaconfig_8540.html" target="_blank">Spring and Thymeleaf with JavaConfig</a></td>
                    </tr>
                    <tr class="row">
                        <td class="col-sm-3 col-md-3 col-lg-3"><p>Unit testing</p></td>
                        <td class="col-sm-9 col-md-9 col-lg-9"><a href="//www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-configuration/" target="_blank">Unit Testing of Spring MVC Controllers</a></td>
                    </tr>
                    <tr class="row">
                        <td class="col-sm-3 col-md-3 col-lg-3"><p>Exception handling</p></td>
                        <td class="col-sm-9 col-md-9 col-lg-9"><a href="//spring.io/blog/2013/11/01/exception-handling-in-spring-mvc" target="_blank">Exception Handling in Spring MVC</a></td>
                    </tr>
                    <!-- http://www.intertech.com/Blog/understanding-spring-mvc-model-and-session-attributes/ -->
                    <!-- http://jackson.codehaus.org/ -->
                    <!-- http://www.programming-free.com/2014/03/spring-mvc-40-restful-web-service-json.html -->
                    <!-- http://www.mkyong.com/spring-mvc/spring-3-mvc-and-json-example/ -->
                    <!-- http://xpadro.blogspot.co.uk/2014/01/migrating-spring-mvc-restful-web.html -->
                    <!-- http://docs.spring.io/spring-roo/reference/html/base-json.html -->
                </table>
            </div>
        </div>
    </body>
</html>