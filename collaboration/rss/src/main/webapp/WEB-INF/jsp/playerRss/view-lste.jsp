<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 

<%@ page isELIgnored="false" %>

<div>
    <ul class="list-unstyled">
		<c:forEach var="item" items="${items}" varStatus="status">
			<li>
				<p>
					<a target="_blank" href="${item.link}">${item.title}</a>
 					<c:set var="lastDate"><fmt:formatDate value="${item.date}"  type="date" dateStyle="SHORT"/></c:set>
 
					<span class="date">(${lastDate})</span>
					<br>
					${item.description}
				</p>
			</li>
		</c:forEach>
	</ul>
</div>
