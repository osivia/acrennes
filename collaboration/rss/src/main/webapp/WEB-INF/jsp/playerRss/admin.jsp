<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op"
	uri="http://www.osivia.org/jsp/taglib/osivia-portal"%>

<%@ page isELIgnored="false"%>

<portlet:defineObjects />
<portlet:actionURL name="save" var="save" />

<form:form action="${save}" method="post"
	modelAttribute="form" cssClass="form-horizontal" role="form" style="height:600px;">

	<div class="container" >
		<div class="container">
			<div class="form-group row">
				<form:label path="nbItems" class="col-sm-2 col-form-label"><op:translate key="NB_ITEMS" /></form:label>
				<div class="col-sm-10 pl-0">
					<form:input type="text" id="nbItems" maxlength="3" size="6" path="nbItems" />
				</div>
			</div>
		</div>
		
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
			<div class="form-group float-right">
				<!-- Cancel -->
				<button type="button" class="btn btn-secondary mr-1" onclick="closeFancybox()"><op:translate key="CANCEL" /></button>
				<!-- Validate -->
				<button type="submit" name="save" class="btn btn-primary">
					<op:translate key="VALIDATE" />
				</button>			
			</div>
		</div>
	</div>	

	<div class="container" >
		<div class="container">
			<table class="table table-condensed table-hover">
				<c:if test="${empty form.sortFeeds}">
					<span><op:translate key="LIST_FEED_NO_RESULT" /></span>
				</c:if>
	
				<c:if test="${not empty form.sortFeeds}">
					<thead>
						<tr>
							<th><op:translate key="NAME_TITLE" /></th>
							<th><op:translate key="RIGHT_TITLE" /></th>
							<th></th>
							<th><op:translate key="PICTURE_TITLE" /></th>
						</tr>
					</thead>
	
	
					<c:forEach var="feed" items="${form.sortFeeds}">
						<tr>
 							<c:forEach var="flux" items="${feed.key}" varStatus="status">
								<c:if test="${status.index gt 0}">
									<!-- Display Name -->
									<td>${flux}</td>
								</c:if>
								<c:if test="${status.index lt 1}">
									<c:set var="syncId" value="${flux}" />
								</c:if>
							</c:forEach>
							<td>
 								<c:forEach var="right" items="${feed.value}" varStatus="status">
									<c:if test="${status.index gt 0}">
										<span>,${right}</span>
									</c:if>
									<c:if test="${status.index lt 1}">
										<span>${right}</span>
									</c:if>
								</c:forEach>
							</td>
							<td>
								<c:set var="delTitle">
									<op:translate key="DEL_FEED" />
								</c:set> 
								<c:set var="editTitle">
									<op:translate key="MOD_FEED" />
								</c:set> 
								<button type="submit" name="del" class="btn btn-secondary" title="${delTitle}" value="${syncId}">
									<i class="glyphicons glyphicons-remove"></i>
								</button>
								<button type="submit" name="edit" class="btn btn-secondary" title="${editTitle}" value="${syncId}">
									<i class="glyphicons glyphicons-pencil"></i>
								</button>							
							</td>
							<td>Coucou</td>
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
				<button type="submit" name="add" class="btn btn-danger" title="${addTitle}">
					<i class="glyphicons glyphicons-plus"></i>
				</button>
			</div>
		</div>
	</div>
	
</form:form>