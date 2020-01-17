<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op"
	uri="http://www.osivia.org/jsp/taglib/osivia-portal"%>

<%@ page isELIgnored="false"%>

<portlet:defineObjects />
<portlet:actionURL name="save" var="saveProperties" />
<portlet:renderURL var="add">
	<portlet:param name="add" value="feed" />
	<portlet:param name="nbitems" value="${nbItems}" />
	<portlet:param name="view" value="${form.viewRss}" />	
</portlet:renderURL>

<form:form action="${saveProperties}" method="post"
	modelAttribute="form" cssClass="form-horizontal" role="form" style="height:600px;">

	<div class="container">
		<div class="form-group row">
			<form:label path="nbItems" class="col-sm-2 col-form-label"><op:translate key="NB_ITEMS" /></form:label>
			<div class="col-sm-10 pl-0">
				<form:input type="text" id="nbItems" maxlength="3" size="6" path="nbItems" />
			</div>
		</div>
	</div>
	
<portlet:renderURL var="add">
	<portlet:param name="add" value="feed" />
	<portlet:param name="nbitems" value="" />
	<portlet:param name="view" value="${form.viewRss}" />	
</portlet:renderURL>	

	<div class="container">
		<div class="form-group row">
			<label for="viewRss" class="col-sm-2 col-form-label"><op:translate key="DISPLAY_MODE" /></label>
			 
			<div class="form-check form-check-inline">
				<form:radiobutton path="viewRss" value="liste" /><op:translate key="LIST_RSS" />
			</div>
			<div class="form-check form-check-inline">
				<form:radiobutton path="viewRss" value="slider" /><op:translate key="SLIDER_RSS" />
			</div>
		</div>
	</div>

	<div class="container">
		<table class="table table-condensed table-hover">
			<c:if test="${empty form.mapFeeds}">
				<span><op:translate key="LIST_FEED_NO_RESULT" /></span>
			</c:if>

			<c:if test="${not empty form.mapFeeds}">
				<thead>
					<tr>
						<th><op:translate key="NAME_TITLE" /></th>
						<th><op:translate key="RIGHT_TITLE" /></th>
						<th></th>
					</tr>
				</thead>


				<c:forEach var="feeds" items="${form.mapFeeds}">
					<tr>
						<c:forEach var="flux" items="${feeds.key}" varStatus="status">
							<c:if test="${status.index gt 0}">
								<!-- Display Name -->
								<td>${flux}</td>
							</c:if>
							<c:if test="${status.index lt 1}">
								<c:set var="syncId" value="${flux}" />
							</c:if>
						</c:forEach>
						<td>
							<c:forEach var="right" items="${feeds.value}" varStatus="status">
								<c:if test="${status.index gt 0}">
									<span>,${right}</span>
								</c:if>
								<c:if test="${status.index lt 1}">
									<span>${right}</span>
								</c:if>
							</c:forEach>
						</td>
						<td>
							<portlet:renderURL var="editFeed">
								<portlet:param name="edit" value="feed" />
								<portlet:param name="id" value="${feeds.key}" />
								<portlet:param name="nbitems" value="nbItems" />
								<portlet:param name="view" value="${viewRss}" />
							</portlet:renderURL>
							<portlet:actionURL name="del" var="del" copyCurrentRenderParameters="true">
								<portlet:param name="id" value="${syncId}" />
							</portlet:actionURL>	
							<c:set var="delTitle">
								<op:translate key="DEL_FEED" />
							</c:set> 
							<a href="${del}" class="btn btn-secondary" title="${delTitle}"> 
								<i class="glyphicons glyphicons-remove"></i> 
							</a>
							
							<c:set var="editTitle">
								<op:translate key="MOD_FEED" />
							</c:set> 
							<a href="${editFeed}" class="btn btn-secondary" title="${editTitle}"> 
								<i class="glyphicons glyphicons-pencil"></i> 
							</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>
	
	<div class="container">
		<c:set var="addTitle">
			<op:translate key="ADD_FEED" />
		</c:set>	
		<div class="form-group pl-3">
			<a href="${add}" class="btn btn-danger" title="${addTitle}"> 
				<i class="glyphicons glyphicons-plus"></i> 
				<span class="sr-only"><op:translate	key="ADD_FEED" /></span>
			</a>
		</div>
	
		<div class="form-group float-right">
			<!-- Cancel -->
			<button type="button" class="btn btn-secondary mr-1" onclick="closeFancybox()"><op:translate key="CANCEL" /></button>
			<!-- Validate -->
			<button type="submit" name="saveProperties" class="btn btn-primary">
				<op:translate key="VALIDATE" />
			</button>			
		</div>
	</div>
</form:form>