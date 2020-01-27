<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<portlet:actionURL name="submit" var="submitUrl">
    <portlet:param name="view" value="settings"/>
</portlet:actionURL>


<div class="cua">
    <div class="card bg-light">
        <div class="card-body">
            <h3 class="card-title"><op:translate key="CUA_CLIENT_SETTINGS_TITLE"/></h3>
            <p class="card-text"><op:translate key="CUA_CLIENT_SETTINGS_HELP"/></p>

            <form:form action="${submitUrl}" method="post" modelAttribute="settingsForm">
                <%--Starred applications--%>
                <div class="form-group">
                    <label><op:translate key="CUA_CLIENT_STARRED_APPLICATIONS"/></label>
                    <c:choose>
                        <c:when test="${empty settingsForm.starredApplications}">
                            <div class="form-control-plaintext text-muted"><op:translate
                                    key="CUA_CLIENT_NO_STARRED_APPLICATIONS"/></div>
                        </c:when>

                        <c:otherwise>
                            <div class="row row-cols-1 row-cols-sm-2 row-cols-xl-3 mx-n2 cua-sortable" data-starred="true">
                                <c:forEach var="application" items="${settingsForm.starredApplications}"
                                           varStatus="status">
                                    <div class="col d-flex flex-column mb-3 px-2" data-id="${application.id}">
                                        <div class="card flex-grow-1">
                                            <div class="card-body d-flex flex-column">
                                                    <%--Title--%>
                                                <h3 class="h5 card-title">
                                                    <span><c:out value="${application.title}"/></span>
                                                </h3>

                                                    <%--Description--%>
                                                <c:if test="${not empty application.description}">
                                                    <p class="card-text"><c:out value="${application.description}"/></p>
                                                </c:if>

                                                <div class="d-flex align-items-end flex-grow-1">
                                                    <portlet:actionURL var="url" name="remove-star"
                                                                       copyCurrentRenderParameters="true">
                                                        <portlet:param name="id" value="${application.id}"/>
                                                    </portlet:actionURL>
                                                    <a href="${url}" class="btn btn-outline-danger btn-sm">
                                                        <i class="glyphicons glyphicons-basic-minus"></i>
                                                        <span><op:translate
                                                                key="CUA_CLIENT_REMOVE_STARRED_APPLICATION"/></span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>

                                        <form:hidden path="starredApplications[${status.index}].order"/>
                                        <form:hidden path="starredApplications[${status.index}].starred"/>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>


                <%--Other applications--%>
                <div class="form-group">
                    <label><op:translate key="CUA_CLIENT_OTHER_APPLICATIONS"/></label>
                    <c:choose>
                        <c:when test="${empty settingsForm.otherApplications}">
                            <div class="form-control-plaintext text-muted"><op:translate
                                    key="CUA_CLIENT_NO_OTHER_APPLICATIONS"/></div>
                        </c:when>

                        <c:otherwise>
                            <div class="row row-cols-1 row-cols-sm-2 row-cols-xl-3 mx-n2 cua-sortable" data-starred="false">
                                <c:forEach var="application" items="${settingsForm.otherApplications}" varStatus="status">
                                    <div class="col d-flex flex-column mb-3 px-2" data-id="${application.id}">
                                        <div class="card flex-grow-1">
                                            <div class="card-body d-flex flex-column">
                                                    <%--Title--%>
                                                <h3 class="h5 card-title">
                                                    <span><c:out value="${application.title}"/></span>
                                                </h3>

                                                    <%--Description--%>
                                                <c:if test="${not empty application.description}">
                                                    <p class="card-text"><c:out value="${application.description}"/></p>
                                                </c:if>

                                                <div class="d-flex align-items-end flex-grow-1">
                                                    <portlet:actionURL var="url" name="add-star"
                                                                       copyCurrentRenderParameters="true">
                                                        <portlet:param name="id" value="${application.id}"/>
                                                    </portlet:actionURL>
                                                    <a href="${url}" class="btn btn-outline-success btn-sm">
                                                        <i class="glyphicons glyphicons-basic-plus"></i>
                                                        <span><op:translate
                                                                key="CUA_CLIENT_ADD_STARRED_APPLICATION"/></span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>

                                        <form:hidden path="otherApplications[${status.index}].order"/>
                                        <form:hidden path="otherApplications[${status.index}].starred"/>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>


                <div class="text-right">
                        <%--Cancel--%>
                    <button type="submit" name="cancel" class="btn btn-secondary">
                        <span><op:translate key="CANCEL"/></span>
                    </button>

                        <%--Save--%>
                    <button type="submit" name="save" class="btn btn-primary">
                        <span><op:translate key="SAVE"/></span>
                    </button>
                </div>


                <input type="submit" name="reorder" class="d-none">
            </form:form>
        </div>
    </div>
</div>
