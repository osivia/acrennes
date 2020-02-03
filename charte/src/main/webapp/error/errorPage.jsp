<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<c:set var="brand"><op:translate key="BRAND"/></c:set>


<html>

<head>
    <title><op:translate key="ERROR"/> - ${brand}</title>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>


<body>
    <h1 class="h4 text-danger">
        <span><op:translate key="ERROR"/></span>
    </h1>
    <h2 class="h5 mb-3 text-danger">
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
        <p>
            <span><op:translate key="ERROR_${param['httpCode']}_MESSAGE"/></span>
        </p>
    </c:if>

    <p>
        <a href="/">
            <span><op:translate key="BACK_TO_HOME"/></span>
        </a>
    </p>
</body>

</html>
