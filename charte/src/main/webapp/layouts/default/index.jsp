<!DOCTYPE html>
<%@ taglib prefix="p" uri="portal-layout" %>

<%@ page contentType="text/html" isELIgnored="false" %>


<html>

<head>
    <%@ include file="../includes/head.jspf" %>
</head>


<body>

<%@ include file="../includes/header.jspf" %>

<main>
    <div class="container-fluid">
        <p:region regionName="top"/>

        <div class="row">
            <div class="col-md">
                <p:region regionName="col-1"/>
            </div>

            <div class="col-md">
                <p:region regionName="col-2"/>
            </div>
        </div>
    </div>
</main>

<%@ include file="../includes/footer.jspf"%>

</body>

</html>
