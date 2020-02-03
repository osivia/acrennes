<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="op"  uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<div class="container">
    <div class="row no-gutters">
        <div class="col-3 d-flex flex-column border">
            <div>
				<portlet:actionURL name="select" var="select" copyCurrentRenderParameters="true">
				</portlet:actionURL>
				<c:if test="${fn:length(player.feeds) gt 1}">
					<a href="${select}" class="btn btn-block btn-slider pl-0" role="button">
					  <img src="/toutatice-portail-acrennes-collaboration-rss/img/infinity.png" width="30" class="pl-1 pr-2"/><op:translate key="ALL_PART" />
		            </a>				
				</c:if>
            </div>

            <c:forEach var="feed" items="${player.feeds}" varStatus="status">
                <div>
					<portlet:actionURL name="select" var="select" copyCurrentRenderParameters="true">
						<portlet:param name="id" value="${feed.id}" />
					</portlet:actionURL>
                    <a href="${select}" class="btn btn-block btn-slider pl-0">
						<img src="${feed.pictureUrl}" width="30" class="pl-1 pr-2"/>
                        <span>${feed.displayName}</span>
                    </a>
                </div>
            </c:forEach>
        </div>

        <div class="col-9 border">
            <ul class="list-unstyled bxslider bxslider-default clearfix bx-wrapper" data-pause="${timer}">
                <c:forEach var="item" items="${player.displayedItems}">
                    <li>
                        <div class="row no-gutters">
                            <div class="col-8">
                                <div class="embed-responsive embed-responsive-21by9">
                                    <c:if test="${not empty item.pictureUrl}">
                                        <img src="${item.pictureUrl}" class="embed-responsive-item" style="object-fit: cover;">
                                    </c:if>
                                </div>
                            </div>

                            <div class="col-4">
                                <div class="position-relative">
                                    <div class="position-absolute p-2">
                                        <h3 class="text-slider">
										<!-- Title + date -->
											<a target="_blank" href="${item.link}">${item.title}
	 				 							<c:set var="lastDate"><fmt:formatDate value="${item.pubDate}" type="date" dateStyle="SHORT"/></c:set>
												<span class="date">(${lastDate})</span>
											</a>
										</h3>
                                        <c:if test="${not empty item.description}">
                                            <p class="mt-2" style="font-size: 14px;text-align: justify;">${item.description}</p>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>