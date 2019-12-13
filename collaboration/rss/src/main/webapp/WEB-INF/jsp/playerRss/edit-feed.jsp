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

<form:form action="${modif}" method="post" modelAttribute="form">
	
	<div class="form-group">

		<c:set var="feedTitle">
			<op:translate key="FEED_PLACEHOLDER" />
		</c:set>
		<c:set var="rigthTitle">
			<op:translate key="RIGHTS_PLACEHOLDER" />
		</c:set>
		<fieldset>
			<legend align="left">Edition du flux</legend>
			<div class="row">
				<div class="col-sm-4 col-lg-4"><span>${form.title}</span></div>
				<div class="col-sm-4 col-lg-4">
					<label for="id_label_single"> <form:select
							cssClass="select2 select2-default" path="rights"
							data-placeholder="${rigthTitle}" multiple="multiple">
							<c:forEach var="right" items="${form.rights}">
								<form:option value="${right}">${right}</form:option>
							</c:forEach>
						</form:select>
					</label>
				</div>
			</div>
		</fieldset>
	</div>

	<div>
		<!-- Cancel -->
		<a href="${cancelUrl}" class="btn btn-default">
		 <span><op:translate key="CANCEL" /></span>
		</a>
		<button type="submit" name="modif" class="btn btn-primary">
			<op:translate key="MOD_FEED" />
		</button>
	</div>

</form:form>