<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
Please complete your registration:<br />
<form action="<c:url value="/auth/completeRegistration" />" method="POST">
    <table>
    <tr><td>Email: </td><td><input type="text" name="email" value="${user.email}" readonly /></td></tr>
    </table>
    <input type="submit" value="Sign up" class="btn" style="margin-top: 4px;"/>
</form>
</t:template>