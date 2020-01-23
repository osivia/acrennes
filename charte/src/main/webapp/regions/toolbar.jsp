<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<nav class="navbar navbar-expand-md navbar-dark bg-primary">
    <%--Brand--%>
    <a href="${requestScope['osivia.home.url']}" class="navbar-brand">
        <span><op:translate key="BRAND"/></span>
    </a>

    <%--Navbar toggler--%>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#toolbar-navbar">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div id="toolbar-navbar" class="collapse navbar-collapse">
        <%--Administration--%>
        <ul class="navbar-nav d-none d-md-flex mr-3">
            <li class="nav-item">
                <c:out value="${requestScope['osivia.toolbar.administrationContent']}" escapeXml="false"/>
            </li>
        </ul>

        <ul class="navbar-nav ml-auto">
            <c:choose>
                <c:when test="${empty requestScope['osivia.toolbar.principal']}">
                    <%--Login--%>
                    <li class="nav-item">
                        <a href="${requestScope['osivia.toolbar.loginURL']}" class="nav-link">
                            <i class="glyphicons glyphicons-basic-log-in"></i>
                            <span class="d-md-none d-lg-inline"><op:translate key="TOOLBAR_LOGIN"/></span>
                        </a>
                    </li>
                </c:when>

                <c:otherwise>
                    <%--Tasks--%>
                    <c:if test="${not empty requestScope['osivia.toolbar.tasks.url']}">
                        <c:set var="title"><op:translate key="NOTIFICATION_TASKS"/></c:set>
                        <li class="nav-item mt-2 mt-md-0">
                            <a href="javascript:"
                               class="nav-link ${requestScope['osivia.toolbar.tasks.count'] gt 0 ? 'text-warning' : ''}"
                               data-target="#osivia-modal" data-load-url="${requestScope['osivia.toolbar.tasks.url']}"
                               data-load-callback-function="tasksModalCallback" data-title="${title}" data-footer="true">
                                <c:choose>
                                    <c:when test="${requestScope['osivia.toolbar.tasks.count'] gt 0}">
                                        <i class="glyphicons glyphicons-basic-bell-ringing"></i>
                                    </c:when>

                                    <c:otherwise>
                                        <i class="glyphicons glyphicons-basic-bell"></i>
                                    </c:otherwise>
                                </c:choose>

                                <span class="d-md-none">${title}</span>
                            </a>
                        </li>
                    </c:if>

                    <%--User menu--%>
                    <li class="nav-item mt-2 mt-md-0 dropdown">
                        <a href="javascript:" class="nav-link dropdown-toggle" data-toggle="dropdown">
                            <c:choose>
                                <c:when test="${empty requestScope['osivia.toolbar.person']}">
                                    <span class="d-md-none d-lg-inline">${requestScope['osivia.toolbar.principal']}</span>
                                </c:when>

                                <c:otherwise>
                                    <span class="d-md-none d-lg-inline">${requestScope['osivia.toolbar.person'].displayName}</span>
                                </c:otherwise>
                            </c:choose>
                        </a>

                        <div class="dropdown-menu dropdown-menu-right">
                            <div class="dropdown-header d-lg-none">${empty requestScope['osivia.toolbar.person'] ? requestScope['osivia.toolbar.principal'] : requestScope['osivia.toolbar.person'].cn}</div>

                            <%--User account--%>
                            <c:set var="url" value="${requestScope['osivia.my-account.url']}"/>
                            <c:if test="${not empty url}">
                                <a href="${url}" class="dropdown-item">
                                    <i class="glyphicons glyphicons-basic-id-badge"></i>
                                    <span><op:translate key="TOOLBAR_USER_ACCOUNT"/></span>
                                </a>
                            </c:if>

                            <%--Logout--%>
                            <a href="javascript:" onclick="logout()" class="dropdown-item">
                                <i class="glyphicons glyphicons-basic-log-out"></i>
                                <span class="d-md-none d-lg-inline"><op:translate key="TOOLBAR_LOGOUT"/></span>
                            </a>
                        </div>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>


<%--Disconnection modal--%>
<div id="disconnection" class="modal fade" data-apps="${op:join(requestScope['osivia.sso.applications'], '|')}" data-redirection="${requestScope['osivia.toolbar.signOutURL']}">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <i class="glyphicons glyphicons-basic-door"></i>
                <span><op:translate key="TOOLBAR_LOGOUT_MESSAGE"/></span>
            </div>
        </div>
    </div>

    <div class="apps-container d-none"></div>
</div>
