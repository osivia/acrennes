<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page isELIgnored="false" %>


<div class="card">
	<div class="card-header">
		<ul class="nav nav-pills card-header-pills">
			<c:if test="${fn:length(player.feeds) gt 1}">
				<portlet:actionURL name="select" var="selectUrl" />

				<li class="nav-item">
					<a href="${selectUrl}" class="nav-link ${empty player.selectedId ? 'active' : ''}">
						<span><op:translate key="ALL_PART" /></span>
					</a>
				</li>
			</c:if>

			<c:forEach var="feed" items="${player.feeds}" varStatus="status">
				<portlet:actionURL name="select" var="select">
					<portlet:param name="id" value="${feed.id}" />
				</portlet:actionURL>

				<li class="nav-item">
					<a href="${select}" class="nav-link ${player.selectedId eq feed.id ? 'active' : ''}">
						<span>${feed.displayName}</span>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>

	<ul class="list-group list-group-flush">
		<c:forEach var="item" items="${player.displayedItems}">
			<li class="list-group-item">
				<h3 class="h6 mb-1">
					<!-- Title + date -->
					<a href="${item.link}" target="_blank" class="no-ajax-link">
						<span>${item.title}</span>
					</a>
				</h3>

				<p class="mb-2">
					<small class="text-muted"><fmt:formatDate value="${item.pubDate}" type="date" dateStyle="long" /></small>
				</p>

				<c:if test="${not empty item.description}">
					<div>${item.description}</div>
				</c:if>
			</li>
		</c:forEach>
	</ul>
</div>
