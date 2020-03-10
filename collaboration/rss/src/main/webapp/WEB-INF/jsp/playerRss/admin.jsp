<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<portlet:defineObjects/>

<c:set var="namespace"><portlet:namespace/></c:set>

<portlet:actionURL name="submit" var="url"/>


<form:form action="${url}" method="post" modelAttribute="windowPropertiesForm" role="form">
    <div class="form-group">
        <form:label path="nbItems"><op:translate key="NB_ITEMS"/></form:label>
        <form:input type="number" maxlength="3" size="6" path="nbItems" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <label><op:translate key="DISPLAY_MODE"/></label>
        <c:forEach var="view" items="${views}" varStatus="status">
            <div class="form-check">
                <form:radiobutton id="${namespace}-view-${status.index}" path="view" value="${view.id}" cssClass="form-check-input"/>
                <label for="${namespace}-view-${status.index}" class="form-check-label"><op:translate key="${view.key}"/></label>
            </div>
        </c:forEach>
    </div>

    <div class="form-group">
        <label><op:translate key="FEEDS"/></label>
        <div class="card">
            <div class="card-body">
                <table class="table table-sm">
                    <c:if test="${empty windowPropertiesForm.feeds}">
                        <span><op:translate key="LIST_FEED_NO_RESULT"/></span>
                    </c:if>

                    <c:if test="${not empty windowPropertiesForm.feeds}">
                        <thead>
                        <tr>
                            <th><op:translate key="PICTURE_TITLE"/></th>
                            <th><op:translate key="NAME_TITLE"/></th>
                            <th><op:translate key="RIGHT_TITLE"/></th>
                            <th></th>
                        </tr>
                        </thead>

                        <tbody class="selector-sortable">
                            <c:forEach var="feed" items="${windowPropertiesForm.feeds}" varStatus="statusFeed">
                                <tr>
                                	<!-- Picture -->
                                	<td><img width="50" src="${feed.pictureUrl}" alt="" class="img-fluid"></td>
                                    <!-- Display Name -->
                                    <td>${feed.displayName}</td>
                                    <td>
                                        <c:forEach var="right" items="${feed.rights}" varStatus="status">
                                            <c:if test="${status.index gt 0}">
                                                <span>,${right}</span>
                                            </c:if>
                                            <c:if test="${status.index lt 1}">
                                                <span>${right}</span>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:set var="delTitle">
                                            <op:translate key="DEL_FEED"/>
                                        </c:set>
                                        <c:set var="editTitle">
                                            <op:translate key="MOD_FEED"/>
                                        </c:set>
                                        <button type="submit" name="del" class="btn btn-outline-secondary btn-sm"
                                                title="${delTitle}"
                                                value="${feed.id}">
                                            <i class="glyphicons glyphicons-remove"></i>
                                        </button>
                                        <button type="submit" name="edit" class="btn btn-outline-secondary btn-sm"
                                                title="${editTitle}"
                                                value="${feed.id}">
                                            <i class="glyphicons glyphicons-pencil"></i>
                                        </button>
                                    </td>
                                    <form:hidden path="feeds[${statusFeed.index}].order"/>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </c:if>
                    
                </table>

                <button type="submit" name="add" class="btn btn-outline-secondary btn-sm">
                    <i class="glyphicons glyphicons-plus"></i>
                    <span><op:translate key="ADD_FEED"/></span>
                </button>
                <input type="submit" name="reorder" class="d-none">
            </div>
        </div>
    </div>

    <div class="form-group text-right">
        <!-- Cancel -->
        <button type="button" class="btn btn-secondary mr-2" onclick="closeFancybox()"><op:translate
                key="CANCEL"/></button>

        <!-- Validate -->
        <button type="submit" name="save" class="btn btn-primary">
            <op:translate key="VALIDATE"/>
        </button>
    </div>

</form:form>