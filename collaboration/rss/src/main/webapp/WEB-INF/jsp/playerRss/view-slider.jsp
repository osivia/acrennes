<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op"  uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>
<%@ taglib prefix="ttc" uri="http://www.toutatice.fr/jsp/taglib/toutatice" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 

<%@ page isELIgnored="false" %>

<div class="bxslider-container">
	<button type="button" class="btn btn-outline-navy"><op:translate key="ALL_PART" /></button>
    <button type="button" class="btn btn-light"><op:translate key="RENNES_ACA" /></button>
    <button type="button" class="btn btn-light"><op:translate key="AREA_BRETAGNE" /></button>
    <button type="button" class="btn btn-light"><op:translate key="PRIVAT_EDUCATION" /></button>
    
    <ul class="list-unstyled bxslider bxslider-default clearfix" data-pause="${timer}">
        <c:forEach var="item" items="${items}" varStatus="status">
            <li class="bxslider-slide">
                <article class="clearfix">
					<c:set var="thumbnailUrl"><ttc:pictureLink document="${document}" property="ttc:vignette" /></c:set>
					<c:set var="date" value="${item.pubDate}" />
					
					<div class="media">
						<c:if test="${not empty thumbnailUrl}">
							<div class="media-left">
								<img src="${thumbnailUrl}" alt="" class="media-object">
							</div>
						</c:if>
					
						<div class="media-body">
							<!-- Title -->
							<h3 class="h4 media-heading">
                                <span>${item.title}</span>
							</h3>
							
					        <!-- Resume -->
					    	<p>
                                <span>${item.description}</span>
                            </p>		
					
							<!-- Date -->
							<p class="text-muted">
								<span><fmt:formatDate value="${date}" type="date" dateStyle="SHORT" /></span>
							</p>
						</div>
					</div>                	
                </article>
            </li>
        </c:forEach>
    </ul>
</div>