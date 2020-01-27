<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<portlet:actionURL name="submit" var="url" copyCurrentRenderParameters="true"/>
<portlet:renderURL var="cancelUrl"/>
<portlet:resourceURL id="loadGroup" var="loadUrl"/>


<form:form action="${url}" method="post" modelAttribute="feedForm">
    <fieldset>
        <legend><op:translate key="Title_Edition"/></legend>

        <div class="form-group">
            <form:label path="displayName"><op:translate key="LABEL_FEED"/></form:label>
            <form:input path="displayName" cssClass="form-control-plaintext" readonly="true" />
        </div>

        <div class="form-group">
            <form:label path="rights"><op:translate key="LABEL_RIGHT"/></form:label>
            <c:set var="placeholder"><op:translate key="RIGHTS_PLACEHOLDER"/></c:set>
            <form:select cssClass="select2 select2-rss" path="rights" data-placeholder="${placeholder}" data-url="${loadUrl}">
                <c:forEach var="right" items="${feedForm.rights}">
                    <form:option value="${right}">${right}</form:option>
                </c:forEach>
            </form:select>
        </div>
    </fieldset>

    <div class="text-right">
        <a href="${cancelUrl}" class="btn btn-secondary">
            <span><op:translate key="CANCEL"/></span>
        </a>

        <button type="submit" class="btn btn-primary">
            <op:translate key="MOD_FEED"/>
        </button>
    </div>

</form:form>