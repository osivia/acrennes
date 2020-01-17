<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>

<portlet:actionURL name="add" var="add" copyCurrentRenderParameters="true" />
<portlet:renderURL var="cancelUrl">
	<portlet:param name="admin" value="container" />
</portlet:renderURL>
<portlet:resourceURL id="loadGroup" var="loadUrl" />

<div class="rss">
	<form:form action="${add}" method="post" modelAttribute="form" style="height:600px;">
			
		<div class="form-group">
		
			<c:set var="feedTitle">
				<op:translate key="FEED_PLACEHOLDER" />
			</c:set>
			<c:set var="rigthTitle">
				<op:translate key="RIGHTS_PLACEHOLDER" />
			</c:set>
			
			<fieldset>
				<legend><op:translate key="ADD" /></legend>
				
				<div class="form-group">
					<form:label path="feeds"><op:translate key="LABEL_FEED" /></form:label>
					<form:select cssClass="select2 select2-default" path="flux"
						data-placeholder="${feedTitle}">
						<c:forEach var="container" items="${form.containers.containers}">
							<optgroup label="${container.title}">
								<c:forEach var="feed" items="${container.feeds}">
									<form:option value="${feed.id}">${feed.title}</form:option>
								</c:forEach>
							</optgroup>
						</c:forEach>
					</form:select>
				</div>
				
				<div class="form-group">
					<form:label path="feeds">
						<op:translate key="LABEL_RIGHT" />
					</form:label>
					<form:select cssClass="select2 select2-rss" path="rights"
						data-placeholder="${rigthTitle}" data-url="${loadUrl}">
						<c:forEach var="right" items="${form.rights}">
							<form:option value="${right}">${right}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</fieldset>
		</div>
	
		<div class="float-right">
			<!-- Cancel -->
			<a href="${cancelUrl}" class="btn btn-secondary">
			 <span><op:translate key="CANCEL" /></span>
			</a>
			<button type="submit" name="add" class="btn btn-primary">
				<op:translate key="ADD_FEED" />
			</button>
		</div>
	
	</form:form>
</div>