<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op"  uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>
<%@ taglib prefix="ttc" uri="http://www.toutatice.fr/jsp/taglib/toutatice" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 

<%@ page isELIgnored="false" %>

    <div class="row row-eq-height no-gutters row-slider">      
		<div class="col-2 col-eq-height">
			<portlet:actionURL name="viewPart" var="part" copyCurrentRenderParameters="true">
				<portlet:param name="index" value="0" />
				<portlet:param name="part" value="all" />
			</portlet:actionURL>
			<c:if test="${settings.numberButton > 1}">
				<a href="${part}" class="btn btn-block btn-slider ${settings.ind eq 0 ? 'btn-actif' : ''} pl-0" role="button">
				  <img src="/toutatice-portail-acrennes-collaboration-rss/img/infinity.png" width="30" class="pl-1 pr-2"/><op:translate key="ALL_PART" /> 
	  			  <%-- <img src="${settings.picture.url}" width="30" class="pl-1 pr-2"/><op:translate key="ALL_PART" /> --%>	
				</a>
			</c:if>
		    
		    <c:set var="retenue" value="0" />
		    <c:if test="${not empty settings.mapFeeds}">
			    <c:forEach var="feeds" items="${settings.mapFeeds}" varStatus="statusFeed">
					<c:set var="indice" value="${statusFeed.index +1 - retenue}" />
					<c:forEach var="flux" items="${feeds.key}" varStatus="status">
						<c:if test="${status.index gt 0}">
							<c:if test="${settings.partners[statusFeed.index]}">
								<portlet:actionURL name="viewPart" var="part" copyCurrentRenderParameters="true">
									<portlet:param name="index" value="${indice}" />
									<portlet:param name="part" value="${flux}" />
								</portlet:actionURL>						
								<!-- Display Name -->
								<a href="${part}" class="btn btn-block btn-slider btn-style ${settings.ind eq indice ? 'btn-actif' : ''}" role="button">${flux}</a>
							</c:if>
							<c:if test="${!settings.partners[statusFeed.index]}">
								<c:set var="retenue" value="${retenue +1}" />								
							</c:if>
						</c:if>
			    	</c:forEach>
			    </c:forEach>
		    </c:if>
	    </div>
	    
	    <div class="col-10">	
	    	<ul class="list-unstyled bxslider bxslider-default clearfix bx-wrapper" data-pause="${timer}">
	
		        <c:forEach var="item" items="${items}" varStatus="status">
		        
		            <li>
						<c:set var="thumbnailUrl" value="${item.enclosure}"/>
						<c:set var="date" value="${item.pubDate}" />
						<div class="row">
							<div class="col-8 no-gutters pr-0">
									<c:if test="${not empty thumbnailUrl}">
										<div class="media-left">
											<img src="${thumbnailUrl}" alt="" class="img-slider">
										</div>
									</c:if>
							</div>
						
							<div class="col-4 text-slider">
								<!-- Title + date -->
								<a target="_blank" href="${item.link}">${item.title}
		 							<c:set var="lastDate"><fmt:formatDate value="${item.pubDate}" type="date" dateStyle="SHORT"/></c:set>
										<span class="date">(${lastDate})</span>
								</a>
	
								<!-- Resume -->
								<p class="mt-2">${item.description}</p>
	
							</div>
						</div>
		            </li>
		        </c:forEach>
	    </ul>
	</div>
</div>