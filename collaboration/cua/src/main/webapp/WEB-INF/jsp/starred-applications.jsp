<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<c:set var="namespace"><portlet:namespace/></c:set>


<%--Alert--%>
<c:if test="${empty form.starredApplications}">
    <div class="alert alert-info" role="alert">
        <c:set var="link"><a href="javascript:" class="alert-link no-ajax-link" data-target="settings"><op:translate key="CUA_CLIENT_NO_STARRED_APPLICATIONS_LINK"/></a></c:set>
        <span><op:translate key="CUA_CLIENT_NO_STARRED_APPLICATIONS_ALERT" args="${link}"/></span>
    </div>
</c:if>


<c:choose>
    <c:when test="${empty form.starredApplications and empty form.otherApplications}">
        <div class="py-5">
            <p class="text-muted"><op:translate key="CUA_CLIENT_NO_APPLICATIONS"/></p>
        </div>
    </c:when>

    <c:otherwise>
        <div class="row row-cols-1 row-cols-sm-2 row-cols-xl-3 mx-n2">
            <c:forEach var="application" items="${empty form.starredApplications ? form.otherApplications : form.starredApplications}">
                <%@ include file="application.jspf" %>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>


<%--Actions--%>
<div class="d-flex">
    <c:if test="${not empty form.starredApplications}">
        <p class="mr-3">
            <a href="#${namespace}-other-applications" class="btn btn-outline-secondary btn-sm no-ajax-link" data-toggle="collapse">
                <i class="glyphicons glyphicons-basic-more-vertical"></i>
                <span><op:translate key="CUA_CLIENT_VIEW_ALL_APPLICATIONS"/></span>
            </a>
        </p>
    </c:if>

    <p class="ml-auto">
        <a href="javascript:" class="btn btn-outline-secondary btn-sm no-ajax-link" data-target="settings">
            <i class="glyphicons glyphicons-basic-cogwheel"></i>
            <span><op:translate key="CUA_CLIENT_SETTINGS"/></span>
        </a>
    </p>
</div>
