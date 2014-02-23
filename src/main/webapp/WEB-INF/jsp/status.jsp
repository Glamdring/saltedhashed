<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
<div class="jumbotron">
    <c:if test="${success}">
        We confirm that the website ${baseUrl} stores user passwords securely. This means that your password will not be available to hackers.
    </c:if>
    <c:if test="${!success || noData}">
        We cannot confirm whether the website ${baseUrl} stores user passwords securely - either it hasn't provided us enough information or it failed our recent checks.
    </c:if>
</div>
</t:template>