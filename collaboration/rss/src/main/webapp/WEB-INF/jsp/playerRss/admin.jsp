<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>

<portlet:defineObjects/>
<portlet:actionURL name="save" var="saveUrl" />

<form:form action="${saveUrl}" method="post" modelAttribute="form" cssClass="form-horizontal" role="form">

	<label><op:translate key="NB_ITEMS" /></label>
	<input type="text" name="nbItems" id="nbItems" size="3" value="${nbItems}" class="form-control">
	
	<label><op:translate key="DISPLAY_MODE" /></label><br /> 
	<input type="radio" name="viewRss" checked/>
	<label for="liste"><op:translate key="LIST_RSS" /></label>
	<input type="radio" name="viewRss" value="slider"/>
  	<label for="slider"><op:translate key="SLIDER_RSS" /></label>
	
	<div class="form-group">
		<ul class="list-unstyled">
			<li></li>
			<li></li>
		</ul>
	</div> 
	
	<div class="form-group">

		<c:set var="placeholder">
			<op:translate key="SEARCH_DOCUMENT_PLACEHOLDER" />
		</c:set>
		<c:set var="placeholder2">
			<op:translate key="FEED_PLACEHOLDER" />
		</c:set>
		<div class="col-sm-9 col-lg-10">
			<div class="input-group">
				<form:select cssClass="select2 select2-default" path="containers" data-placeholder="${placeholder}">
					<c:forEach var="container" items="${containers.containers}">
						<optgroup label="${container.title}">
							<c:forEach var="feed" items="${container.feeds}">
								<form:option value="${feed.id}">${feed.title}</form:option>
							</c:forEach>
						</optgroup> 
					</c:forEach>
				</form:select>
				<form:select cssClass="select2 select2-default" path="right" data-placeholder="${placeholder2}" multiple="multiple">
					<c:forEach var="right" items="${righs}">
						<form:option value="${right}">${right}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
	</div>
		
	<input type="submit" name="modifierPrefs"  value="Valider">
	<input type="submit" name="annuler"  value="Annuler">
</form:form>