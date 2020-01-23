<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<c:choose>
    <c:when test="${empty form.otherApplications}">
        <div class="py-5">
            <p class="text-muted"><op:translate key="CUA_CLIENT_NO_OTHER_APPLICATIONS"/></p>
        </div>
    </c:when>

    <c:otherwise>
        <div class="row row-cols-1 row-cols-sm-2 row-cols-xl-3 mx-n2">
            <c:forEach var="application" items="${form.otherApplications}">
                <%@ include file="application.jspf" %>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>
