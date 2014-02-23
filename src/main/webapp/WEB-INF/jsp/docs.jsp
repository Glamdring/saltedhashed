<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="currentPage" value="docs" scope="request" />

<t:template>
    <%@ include file="docs-include.jsp" %>
</t:template>