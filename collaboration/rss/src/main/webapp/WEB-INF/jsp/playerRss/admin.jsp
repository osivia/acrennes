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
</portlet:renderURL>

<form:form action="${saveProperties}" method="post"
	modelAttribute="form" cssClass="form-horizontal" role="form">

	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-lg-6">
				<form:label path="nbItems"><op:translate key="NB_ITEMS" /></form:label>
			</div>
			<div class="col-sm-6 col-lg-6">
				<form:input type="text" id="nbItems" maxlength="3"
					size="6" class="form-control" path="nbItems" />
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-sm-3 col-lg-3">
				<form:label path="viewRss"><op:translate key="DISPLAY_MODE" /></form:label><br />
			</div>
			<div class="col-sm-4 col-lg-4">
				<form:radiobutton path="viewRss" value="liste" /><op:translate key="LIST_RSS" />
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
							</portlet:renderURL>
							<portlet:actionURL name="del" var="del" copyCurrentRenderParameters="true">
								<portlet:param name="id" value="${syncId}" />
							</portlet:actionURL>	
							<c:set var="delTitle">
								<op:translate key="DEL_FEED" />
							</c:set> 
							<a href="${del}" class="btn btn-default" title="${delTitle}"> 
								<i class="glyphicons glyphicons-remove"></i> 
							</a>
							
							<c:set var="editTitle">
								<op:translate key="MOD_FEED" />
							</c:set> 
							<a href="${editFeed}" class="btn btn-default" title="${editTitle}"> 
								<i class="glyphicons glyphicons-pencil"></i> 
							</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>
	
	<c:set var="addTitle">
		<op:translate key="ADD_FEED" />
	</c:set>	
	<div class="form-group">
		<a href="${add}" class="btn btn-danger" title="${addTitle}"> 
			<i class="glyphicons glyphicons-plus"></i> 
			<span class="sr-only"><op:translate	key="ADD_FEED" /></span>
		</a>
	</div>

	<div class="form-group">
		<!-- Cancel -->
		<button type="button" class="btn btn-default" onclick="closeFancybox()"><op:translate key="CANCEL" /></button>
		<!-- Validate -->
		<input type="submit" name="saveProperties" value="Valider"
			class="btn btn-primary">
	</div>
</form:form>