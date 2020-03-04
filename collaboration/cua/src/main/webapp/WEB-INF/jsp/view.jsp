<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<c:set var="namespace"><portlet:namespace/></c:set>

<portlet:renderURL var="settingsUrl">
    <portlet:param name="view" value="settings"/>
</portlet:renderURL>

<c:set var="errorMessage"><op:translate key="CUA_CLIENT_ERROR_MESSAGE"/></c:set>


<div class="cua" data-settings-url="${settingsUrl}" data-error-message="${errorMessage}">
    <c:choose>
        <c:when test="${form.partiallyLoaded}">
            <%@ include file="starred-applications.jsp" %>
        </c:when>

        <c:otherwise>
            <portlet:resourceURL var="url" id="load" />

            <%--Placeholder--%>
            <div class="cua-placeholder" data-url="${url}">
                <div class="d-flex justify-content-center py-5">
                    <div class="spinner-border text-secondary" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <div id="${namespace}-other-applications" class="collapse">
        <portlet:resourceURL var="url" id="load-other-applications" />

        <%--Placeholder--%>
        <div class="cua-placeholder-other-applications" data-url="${url}">
            <div class="d-flex justify-content-center py-5">
                <div class="spinner-border text-secondary" role="status">
                    <span class="sr-only">Loading...</span>
                </div>
            </div>
        </div>
    </div>
</div>
