<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes.jsp"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:template>
    <div class="jumbotron">
        <c:if test="${success}">
            <h3 class="success">We confirm that the website ${param.baseUrl} stores user passwords
                securely. This means that your password will not be available to
                hackers.</h3>
        </c:if>
        <c:if test="${!success || noData}">
            <h3 class="failure">We cannot confirm whether the website ${param.baseUrl} stores user
                passwords securely - either it hasn't provided us enough information
                or it failed our recent checks.</h3>
        </c:if>
    </div>
    <div class="lead">
        <p>Why is this important?</p>
        <p>Many websites store your password insecurely. When they are
            attacked, your password may leak into the hands of hackers. If you
            reuse the same password for multiple sites (most of the users do
            that), and one of the sites is vulnerable, then the attacker has your
            credentials for all other sites.</p>
        <p>
            Every site can be insecure, even big ones, like Adobe, Yahoo, Gawker,
            LinkedIn and <a href="https://haveibeenpwned.com/PwnedWebsites">many
                more</a>. You can check if your password has leaked from big sites <a
                href="https://haveibeenpwned.com/">here</a>.
        </p>
    </div>
</t:template>