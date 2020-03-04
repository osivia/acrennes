<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<portlet:renderURL var="addUrl">
    <portlet:param name="view" value="add"/>
</portlet:renderURL>

<portlet:actionURL name="submit" var="submitUrl"/>


<div class="layout-selector">
    <form:form action="${submitUrl}" method="post" modelAttribute="adminForm">
        <legend><op:translate key="LAYOUT_SELECTOR_ADMIN_LEGEND"/></legend>

        <%--Items--%>
        <div class="form-group">
            <label><op:translate key="LAYOUT_SELECTOR_ADMIN_ITEMS_LABEL"/></label>
            <ol class="layout-selector-sortable">
                <c:forEach var="item" items="${adminForm.items}" varStatus="status">
                    <portlet:renderURL var="editUrl">
                        <portlet:param name="view" value="edit"/>
                        <portlet:param name="id" value="${item.id}"/>
                    </portlet:renderURL>
                    <portlet:actionURL name="remove" var="removeUrl">
                        <portlet:param name="id" value="${item.id}"/>
                    </portlet:actionURL>

                    <li class="d-flex align-items-center flex-wrap">
                        <div class="mr-2">
                            <c:if test="${not empty item.icon}">
                                <i class="${item.icon}"></i>
                            </c:if>
                            <span>${item.label}</span>
                        </div>

                        <div class="mr-2">
                            <small class="text-muted">${item.id}</small>
                        </div>

                        <div class="ml-auto mr-2">
                            <a href="${editUrl}" class="btn btn-outline-secondary btn-sm">
                                <i class="glyphicons glyphicons-basic-square-edit"></i>
                                <span class="d-none d-md-inline"><op:translate
                                        key="LAYOUT_SELECTOR_ADMIN_EDIT_ITEM_BUTTON"/></span>
                            </a>
                        </div>

                        <div>
                            <a href="${removeUrl}" class="btn btn-outline-secondary btn-sm">
                                <i class="glyphicons glyphicons-basic-square-empty-remove"></i>
                                <span class="d-none d-md-inline"><op:translate
                                        key="LAYOUT_SELECTOR_ADMIN_REMOVE_ITEM_BUTTON"/></span>
                            </a>
                        </div>

                        <form:hidden path="items[${status.index}].order"/>
                    </li>
                </c:forEach>
            </ol>
            <p class="form-text">
                <small class="text-muted"><op:translate key="LAYOUT_SELECTOR_ADMIN_ITEMS_HELP"/></small>
            </p>
        </div>

        <%--Add item--%>
        <div class="form-group">
            <p class="form-control-plaintext">
                <a href="${addUrl}" class="btn btn-outline-secondary btn-sm">
                    <i class="glyphicons glyphicons-basic-square-empty-plus"></i>
                    <span><op:translate key="LAYOUT_SELECTOR_ADMIN_ADD_ITEM_BUTTON"/></span>
                </a>
            </p>
        </div>

        <div class="text-right">
            <button type="submit" name="cancel" class="btn btn-secondary">
                <span><op:translate key="CANCEL"/></span>
            </button>

            <button type="submit" name="save" class="btn btn-primary">
                <span><op:translate key="SAVE"/></span>
            </button>
        </div>

        <input type="submit" name="reorder" class="d-none">
    </form:form>
</div>
