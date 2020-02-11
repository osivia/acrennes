<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ page isELIgnored="false" %>


<div class="layout-selector">
    <ol>
        <c:forEach var="item" items="${form.items}">
            <portlet:actionURL var="url" name="select">
                <portlet:param name="id" value="${item.id}"/>
            </portlet:actionURL>

            <li>
                <a class="${url}">
                    <i class="${item.icon}"></i>
                    <span>${item.label}</span>
                </a>
            </li>
        </c:forEach>
    </ol>
</div>