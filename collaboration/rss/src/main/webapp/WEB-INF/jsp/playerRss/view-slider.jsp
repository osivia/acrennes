<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page isELIgnored="false"%>


<c:set var="namespace"><portlet:namespace/></c:set>


<div class="toutatice-slider">
    <div class="row no-gutters">
        <div class="col-3 d-flex flex-column">
            <div class="position-absolute">
                <div>
                    <portlet:actionURL name="select" var="select" />
                    <c:if test="${fn:length(player.feeds) gt 1}">
                        <a href="${select}" class="" role="button">
                            <op:translate key="ALL_PART" />
                        </a>
                    </c:if>
                </div>
            
                <c:forEach var="feed" items="${player.feeds}" varStatus="status">
                    <div>
                        <portlet:actionURL name="select" var="select">
                            <portlet:param name="id" value="${feed.id}" />
                        </portlet:actionURL>
                        <a href="${select}" class="" role="button">
                            <%-- <img src="${feed.pictureUrl}" class="" /> --%>
                            <span>${feed.displayName}</span>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    
        <div class="col-9">
            <div id="${namespace}-slider" class="carousel slide" data-ride="carousel">
                <div class="toutatice-carousel-indicators">
                    <div class="row no-gutters">
                        <div class="col-4 offset-8">
                            <ol class="list-inline text-right mb-2 mr-5 pr-1">
                                <c:forEach var="item" items="${player.displayedItems}" varStatus="status">
                                    <li class="list-inline-item mr-0 ${status.first ? 'active' : ''}">
                                        <a href="javascript:" class="text-secondary" data-target="#${namespace}-slider" data-slide-to="${status.index}">
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
                        <div class="carousel-item ${status.first ? 'active' : ''}">
                            <div class="row no-gutters">
                                <div class="col-8">
                                    <div class="embed-responsive embed-responsive-21by9">
                                        <c:if test="${not empty item.pictureUrl}">
                                            <img src="${item.pictureUrl}" class="embed-responsive-item">
                                        </c:if>
                                    </div>
                                </div>
                                
                                <div class="col-4">
                                    <div class="position-absolute">
                                        <h3 class="h6">
                                            <!-- Title + date -->
                                            <a href="${item.link}" target="_blank" class="no-ajax-link">
                                                <span>${item.title}</span>
                                            </a>
                                            <br>
                                            <small class="text-muted"><fmt:formatDate value="${item.pubDate}" type="date" dateStyle="long" /></small>
                                        </h3>
                                        
                                        <c:if test="${not empty item.description}">
                                            <p class="mt-2">${item.description}</p>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                
                <a href="#${namespace}-slider" class="carousel-control-prev text-tertiary" role="button" data-slide="prev">
                    <i class="glyphicons glyphicons-basic-chevron-left"></i>
                    <span class="sr-only">Previous</span>
                </a>
                <a href="#${namespace}-slider" class="carousel-control-next text-tertiary" role="button" data-slide="next">
                    <i class="glyphicons glyphicons-basic-chevron-right"></i>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
    </div>
</div>
