<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>


<div class="container">
    <div class="row no-gutters">
        <div class="col-3 d-flex flex-column border">
            <div>
                <a href="#" class="btn btn-link">
                    <span>Tous</span>
                </a>
            </div>

            <c:forEach var="feed" items="${player.feeds}">
                <div>
                    <a href="#" class="btn btn-link">
                        <span>${feed.displayName}</span>
                    </a>
                </div>
            </c:forEach>
        </div>

        <div class="col-9 border">
            <ul class="bxslider bxslider-default">
                <c:forEach var="item" items="${player.displayedItems}">
                    <li>
                        <div class="row no-gutters">
                            <div class="col-8">
                                <div class="embed-responsive embed-responsive-21by9">
                                    <c:if test="${not empty item.pictureUrl}">
                                        <img src="${item.pictureUrl}" class="embed-responsive-item" style="object-fit: cover;">
                                    </c:if>
                                </div>
                            </div>

                            <div class="col-4">
                                <div class="position-relative">
                                    <div class="position-absolute">
                                        <h3 class="h5">${item.title}</h3>

                                        <c:if test="${not empty item.description}">
                                            <p>${item.description}</p>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
