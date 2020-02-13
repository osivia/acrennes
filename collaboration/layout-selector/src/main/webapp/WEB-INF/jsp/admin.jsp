<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<portlet:actionURL name="submit" var="submitUrl"/>
<portlet:actionURL name="add" var="addUrl"/>


<div class="layout-selector">
    <form:form action="${submitUrl}" method="post" modelAttribute="adminForm">
        <%--Items--%>
        <div class="form-group">
            <label><op:translate key="LAYOUT_SELECTOR_ADMIN_ITEMS_LABEL"/></label>
            <ol class="layout-selector-sortable">
                <c:forEach var="item" items="${adminForm.items}" varStatus="status">
                    <portlet:actionURL name="edit" var="editUrl">
                        <portlet:param name="id" value="${item.id}"/>
                    </portlet:actionURL>
                    <portlet:actionURL name="remove" var="removeUrl">
                        <portlet:param name="id" value="${item.id}"/>
                    </portlet:actionURL>

                    <li>
                        <span class="mr-2">${item.label}</span>
                        <small class="mr-2 text-muted">${item.id}</small>

                        <a href="${editUrl}" class="btn btn-outline-secondary btn-sm ml-auto mr-2">
                            <i class="glyphicons glyphicons-basic-square-edit"></i>
                            <span><op:translate key="LAYOUT_SELECTOR_ADMIN_EDIT_ITEM_BUTTON"/></span>
                        </a>

                        <a class="${removeUrl}" class="btn btn-outline-secondary btn-sm">
                            <i class="glyphicons glyphicons-basic-square-remove"></i>
                            <span><op:translate key="LAYOUT_SELECTOR_ADMIN_REMOVE_ITEM_BUTTON"/></span>
                        </a>
                    </li>

                    <form:hidden path="items[${status.index}].order"/>
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
                    <i class="glyphicons glyphicons-square-empty-plus"></i>
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
