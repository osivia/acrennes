<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>

<portlet:actionURL name="modif" var="modif" copyCurrentRenderParameters="true" />
<portlet:renderURL var="cancelUrl">
	<portlet:param name="admin" value="container" />
</portlet:renderURL>
<portlet:resourceURL id="loadGroup" var="loadUrl" />

<form:form action="${modif}" method="post" modelAttribute="form" style="height:600px;">
	
	<div class="form-group">

		<c:set var="feedTitle">
			<op:translate key="FEED_PLACEHOLDER" />
		</c:set>
		<c:set var="rigthTitle">
			<op:translate key="RIGHTS_PLACEHOLDER" />
		</c:set>
		<fieldset>
			<legend align="left"><op:translate key="Title_Edition" /></legend>
			<div class="row ">
				<div class="col-sm-2 col-lg-2">
					<form:label path="rights">
						<c:forEach var="feed" items="${form.feeds}" varStatus="status">
							<c:if test="${status.index gt 0}">
								<!-- Display Name -->
								<td>${feed}</td>
							</c:if>
						</c:forEach>
					</form:label>
				</div>
				<div class="col-sm-10 col-lg-10">
					<form:select cssClass="select2 select2-rss" path="rights"
						data-placeholder="${rigthTitle}" data-url="${loadUrl}">
						<c:forEach var="right" items="${form.rights}">
							<form:option value="${right}">${right}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
		</fieldset>
	</div>

	<div class="float-right">
		<!-- Cancel -->
		<a href="${cancelUrl}" class="btn btn-secondary">
		 <span><op:translate key="CANCEL" /></span>
		</a>
		<button type="submit" name="modif" class="btn btn-primary">
			<op:translate key="MOD_FEED" />
		</button>
	</div>

</form:form>