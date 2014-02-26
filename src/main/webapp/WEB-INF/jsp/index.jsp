<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="currentPage" value="home" scope="request" />
<t:template>

<jsp:attribute name="header">
    <c:if test="${userLoggedIn}">
        <%@ include file="sites-header.jsp" %>
    </c:if>
</jsp:attribute>

<jsp:body>
    <c:if test="${!userLoggedIn}">
        <script type="text/javascript">
            $(document).ready(function() {
                var signinLink = $("#personaSignup");
                signinLink.click(function() {
                    userRequestedAuthentication = true;
                    navigator.id.request({siteName: 'SaltedHashed'});
                });
            });
        </script>

        <div class="jumbotron">
            <h2>Prove that you store passwords securely</h2>
            <p class="lead">Storing passwords properly is something not all
            websites are doing. Every now and then we read about leaked password
            and it turns out even big companies are doing it wrong. Do you want
            to prove to your users that you are storing their passwords
            correctly?</p>
            <p>
              <a class="btn btn-lg btn-success" href="javascript:void(0);" id="personaSignup" role="button">Register and get your site verified</a>
            </p>
        </div>
    </c:if>
    <c:if test="${userLoggedIn}">
            <%@ include file="sites-body.jsp" %>
    </c:if>

    <%@ include file="docs-include.jsp" %>
</jsp:body>
</t:template>