<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<portlet:renderURL var="cancelUrl" />

<portlet:actionURL name="submit" var="submitUrl" copyCurrentRenderParameters="true" />

<portlet:resourceURL id="load-profiles" var="loadProfilesUrl"/>


<div class="layout-selector">
    <form:form action="${submitUrl}" method="post" modelAttribute="itemForm">
        <legend><op:translate key="LAYOUT_SELECTOR_ADMIN_ADD_LEGEND"/></legend>

        <%--Identifier--%>
        <div class="form-group required">
            <form:label path="id"><op:translate key="LAYOUT_SELECTOR_ADMIN_ITEM_ID"/></form:label>
            <form:input path="id" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
            <form:errors path="id" cssClass="invalid-feedback"/>
        </div>

        <%--Label--%>
        <div class="form-group required">
            <form:label path="label"><op:translate key="LAYOUT_SELECTOR_ADMIN_ITEM_LABEL"/></form:label>
            <form:input path="label" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
            <form:errors path="label" cssClass="invalid-feedback"/>
        </div>

        <%--Icon--%>
        <div class="form-group">
            <form:label path="icon"><op:translate key="LAYOUT_SELECTOR_ADMIN_ITEM_ICON"/></form:label>
            <form:input path="icon" cssClass="form-control" />
        </div>

        <%--Profiles--%>
        <div class="form-group">
            <form:label path="profiles"><op:translate key="LAYOUT_SELECTOR_ADMIN_ITEM_PROFILES"/></form:label>
            <form:select path="profiles" cssClass="select2 select2-layout-selector" data-url="${loadProfilesUrl}">
                <c:forEach var="profile" items="${itemForm.profiles}">
                    <form:option value="${profile}">${profile}</form:option>
                </c:forEach>
            </form:select>
        </div>

        
        <div class="text-right">
            <a href="${cancelUrl}" class="btn btn-secondary">
                <span><op:translate key="CANCEL"/></span>
            </a>

            <button type="submit" class="btn btn-primary">
                <span><op:translate key="SAVE"/></span>
            </button>
        </div>
    </form:form>
</div>
