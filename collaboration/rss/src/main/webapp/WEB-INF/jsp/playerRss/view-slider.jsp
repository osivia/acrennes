<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page isELIgnored="false"%>


<c:set var="namespace"><portlet:namespace/></c:set>


<div class="toutatice-slider">
    <div class="toutatice-slider-selector">
        <ol class="toutatice-selector">
            <c:if test="${fn:length(player.feeds) gt 1}">
                <portlet:actionURL name="select" var="select" />

                <li class="toutatice-selector-item ${empty player.selectedId ? 'active' : ''}">
                    <c:set var="title"><op:translate key="ALL_PART" /></c:set>
                    <a href="${select}" title="${title}">
                        <span class="toutatice-selector-item-icon d-none d-xl-inline"></span>
                        <span class="d-xl-none"><op:translate key="ALL"/></span>
                        <span class="d-none d-xl-inline">${title}</span>
                    </a>
                </li>
            </c:if>

            <c:forEach var="feed" items="${player.feeds}" varStatus="status">
                <portlet:actionURL name="select" var="select">
                    <portlet:param name="id" value="${feed.id}" />
                </portlet:actionURL>

                <li class="toutatice-selector-item ${player.selectedId eq feed.id ? 'active' : ''}">
                    <a href="${select}" title="${feed.displayName}">
                        <span class="toutatice-selector-item-icon">
                            <c:if test="${not empty feed.pictureUrl}">
                                <img src="${feed.pictureUrl}" class="" />
                            </c:if>
                        </span>
                        <span class="d-none d-xl-inline">${feed.displayName}</span>
                    </a>
                </li>
            </c:forEach>
        </ol>
    </div>

    <div class="toutatice-slider-container">
        <div id="${namespace}-slider" class="carousel slide" data-ride="carousel">
            <div class="toutatice-slider-indicators">
                <div class="row no-gutters">
                    <div class="col-4 offset-8">
                        <ol class="list-inline text-right mb-2 mr-5 pr-1">
                            <c:forEach var="item" items="${player.displayedItems}" varStatus="status">
                                <li class="list-inline-item mr-0 ${status.first ? 'active' : ''}">
                                    <a href="javascript:" class="text-secondary no-ajax-link" data-target="#${namespace}-slider" data-slide-to="${status.index}">
                                        <i class="glyphicons"></i>
                                    </a>
                                </li>
                            </c:forEach>
                        </ol>
                    </div>
                </div>
            </div>

            <div class="carousel-inner">
                <c:forEach var="item" items="${player.displayedItems}" varStatus="status">
                    <c:if test="${not empty item}">
                        <div class="carousel-item ${status.first ? 'active' : ''}">
                            <div class="toutatice-slider-inner-container">
                                <div class="toutatice-slider-picture-container">
                                    <div class="embed-responsive">
                                        <c:if test="${not empty item.pictureUrl}">
                                            <img src="${item.pictureUrl}" class="embed-responsive-item">
                                        </c:if>
                                    </div>
                                </div>
    
                                <div class="toutatice-slider-text-container">
                                    <div>
                                        <h3 class="h6 mb-1">
                                            <!-- Title + date -->
                                            <a href="${item.link}" target="_blank" class="no-ajax-link">
                                                <span>${item.title}</span>
                                            </a>
                                        </h3>

                                        <p class="mb-2">
                                            <small class="text-muted"><fmt:formatDate value="${item.pubDate}" type="date" dateStyle="long" /></small>
                                        </p>
    
                                        <c:if test="${not empty item.description}">
                                            <div class="d-none d-md-block mt-2">${item.description}</div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <a href="#${namespace}-slider" class="carousel-control-prev text-tertiary no-ajax-link" role="button" data-slide="prev">
                            <i class="glyphicons glyphicons-basic-chevron-left"></i>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a href="#${namespace}-slider" class="carousel-control-next text-tertiary no-ajax-link" role="button" data-slide="next">
                            <i class="glyphicons glyphicons-basic-chevron-right"></i>
                            <span class="sr-only">Next</span>
                        </a>                        
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
