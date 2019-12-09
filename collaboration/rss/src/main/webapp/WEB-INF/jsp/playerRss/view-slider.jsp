<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op"  uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>
<%@ taglib prefix="ttc" uri="http://www.toutatice.fr/jsp/taglib/toutatice" %>

<%@ page isELIgnored="false" %>

<div class="bxslider-container">
    <ul class="bxslider bxslider-interactik">
        <c:forEach items="${documents}" var="document">
            <li class="bxslider-slide">
                <div class="row">
                    <div class="slider-interactik">
                        <div class="col-sm-8 col-xs-12">
                            <div class="slider-interactik-image">
                                <c:choose>
                                    <c:when test="${document.type.name eq 'PortalPage'}">
                                        <c:set var="imageURL"><ttc:pictureLink document="${document}" property="ttc:vignette" /></c:set>
                                    </c:when>
                                
                                    <c:when test="${document.type.name eq 'Annonce'}">
                                        <c:set var="imageURL"><ttc:pictureLink document="${document}" property="annonce:image" /></c:set>
                                    </c:when>
                                
                                    <c:otherwise>
                                        <c:remove var="imageURL" />
                                    </c:otherwise>
                                </c:choose>
                                
                                <img src="${imageURL}" alt="" class="img-responsive">
                            </div>
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <h3 class="media-heading">
                                <c:set var="description" value="${document.properties['dc:description']}" />
                                <c:choose>
                                    <c:when test="${empty description}">
                                        <ttc:title document="${document}" />
                                    </c:when>
                                    
                                    <c:otherwise>
                                        <c:set var="url"><ttc:documentLink document="${document}"/></c:set>
                                        <a href="${url}" class="text-pre-wrap no-ajax-link">${description}</a>
                                    </c:otherwise>
                                </c:choose>
                            </h3>
                        </div>
                    </div>
                </div>
            </li>
        </c:forEach>
    </ul>
    
    <div class="bxslider-controls hidden-xs">
		<a href="#" id="PREV" class="bxPrev"></a>
		<a href="#" id="NEXT" class="bxNext"></a>
    </div>

</div>