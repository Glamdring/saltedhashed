<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="currentPage" value="sites" scope="request" />

<t:template>
    <jsp:attribute name="header">
        <%@ include file="sites-header.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%@ include file="sites-body.jsp" %>
    </jsp:body>
</t:template>