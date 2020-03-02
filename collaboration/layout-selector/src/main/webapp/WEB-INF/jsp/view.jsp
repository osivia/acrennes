<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>

<%@ page isELIgnored="false" %>


<div class="layout-selector">
    <ol class="toutatice-selector">
        <c:forEach var="item" items="${form.items}">
            <portlet:actionURL var="url" name="select">
                <portlet:param name="id" value="${item.id}"/>
            </portlet:actionURL>

            <li class="toutatice-selector-item ${form.activeId eq item.id ? 'active' : ''}">
                <a href="${url}">
                    <span class="toutatice-selector-item-icon">
                        <c:if test="${not empty item.icon}">
                            <i class="${item.icon}"></i>
                        </c:if>
                    </span>
                    <span>${item.label}</span>
                </a>
            </li>
        </c:forEach>
    </ol>
</div>
