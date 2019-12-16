<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>


<div class="row row-cols-1 row-cols-sm-2 row-cols-xl-3">
    <c:forEach var="application" items="${form.applications}">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <h3 class="h5 card-title">
                        <a href="${application.url}" target="_blank" class="stretched-link text-decoration-none">${application.title}</a>
                    </h3>

                    <c:if test="${not empty application.description}">
                        <p class="card-text">${application.description}</p>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
