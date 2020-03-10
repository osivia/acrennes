<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ page isELIgnored="false" %>


<portlet:actionURL name="submit" var="submitUrl" />


<div class="toutatice-search">
    <form:form action="${submitUrl}" method="get" modelAttribute="form">
        <div class="form-group">
            <form:label path="query" cssClass="sr-only"><op:translate key="TOUTATICE_SEARCH_LABEL"/></form:label>
            <div class="input-group">
                <div class="input-group-prepend">
                <span class="input-group-text">
                    <i class="glyphicons glyphicons-basic-search"></i>
                </span>
                </div>
                <form:input path="query" cssClass="form-control" />
            </div>
        </div>

        <input type="submit" class="d-none">
    </form:form>
</div>