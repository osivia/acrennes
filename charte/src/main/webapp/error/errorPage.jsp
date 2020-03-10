<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<c:set var="brand"><op:translate key="BRAND"/></c:set>


<html>

<head>
    <title><op:translate key="ERROR"/> - ${brand}</title>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/toutatice.css"/>
</head>


<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8 col-xl-6">
                <div class="card text-center border-danger my-5">
                    <div class="card-body">
                        <h1 class="h4 card-title text-danger">
                            <span><op:translate key="ERROR"/></span>
                        </h1>

                        <h2 class="h5 card-subtitle text-danger">
                            <c:choose>
                                <c:when test="${(param['httpCode'] eq 401) || (param['httpCode'] eq 403) || (param['httpCode'] eq 404) || (param['httpCode'] eq 500)}">
                                    <span>${param['httpCode']}</span>
                                    <small><op:translate key="ERROR_${param['httpCode']}"/></small>
                                </c:when>

                                <c:otherwise>
                                    <small><op:translate key="ERROR_GENERIC_MESSAGE"/></small>
                                </c:otherwise>
                            </c:choose>
                        </h2>

                        <c:if test="${(param['httpCode'] eq 401) || (param['httpCode'] eq 403) || (param['httpCode'] eq 404) || (param['httpCode'] eq 500)}">
                            <p class="card-text">
                                <span><op:translate key="ERROR_${param['httpCode']}_MESSAGE"/></span>
                            </p>
                        </c:if>

                        <a href="/" class="card-link">
                            <span><op:translate key="BACK_TO_HOME"/></span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
