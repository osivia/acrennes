<!DOCTYPE html>
<%@ taglib prefix="p" uri="portal-layout" %>
<%@ taglib prefix="op" uri="http://www.osivia.org/jsp/taglib/osivia-portal" %>

<%@ page contentType="text/html" isELIgnored="false" %>


<html>

<head>
    <%@ include file="../includes/head.jspf" %>
</head>


<body>

<%@ include file="../includes/header.jspf" %>

<main>
    <div class="container py-4">
        <h2 class="h3 mb-3"><op:translate key="LAYOUT_DESKTOP_TITLE"/></h2>

        <p:region regionName="top"/>

        <div class="row no-gutters">
            <div class="col-md-3">
                <div class="mb-3 py-3 bg-white rounded">
                    <p:region regionName="col-1"/>
                </div>
            </div>

            <div class="col-md-9 d-flex flex-column">
                <div class="flex-grow-1 p-3 bg-white rounded">
                    <p:region regionName="col-2"/>
                </div>
            </div>
        </div>

        <p:region regionName="bottom"/>
    </div>
</main>

<%@ include file="../includes/footer.jspf" %>

</body>

</html>
