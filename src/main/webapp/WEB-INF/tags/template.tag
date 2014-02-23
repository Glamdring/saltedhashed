<%@tag description="Main template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>

<%@ include file="../jsp/includes.jsp" %>
<c:set value="${pageContext.request.contextPath}/static" var="staticRoot" scope="request" />
<c:set value="${pageContext.request.contextPath}" var="root" scope="request" />

<c:if test="${root == '//'}">
    <c:set value="" var="root" scope="request" />
    <c:set value="/static" var="staticRoot" scope="request" />
</c:if>

<c:set var="userLoggedIn" value="${userContext.user != null}" scope="request" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../assets/ico/favicon.ico">

<title>SaltedHahshed - prove that your sites store passwords securely</title>

<script type="text/javascript" src="${staticRoot}/scripts/jquery-1.10.2.min.js"></script>
<link rel="stylesheet" href="${staticRoot}/styles/main.css">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

<script type="text/javascript" src="https://login.persona.org/include.js"></script>

<script type="text/javascript">
    var loggedInUser = '${userContext.user != null ? userContext.user.email : null}';
    var userRequestedAuthentication = false;
    $(document).ready(function() {
        navigator.id.watch({
            loggedInUser : loggedInUser,
            onlogin : function(assertion) {
                $.ajax({
                    type : 'POST',
                    url : '${root}/persona/auth',
                    data : {assertion : assertion, userRequestedAuthentication : userRequestedAuthentication},
                    success : function(data) {
                        if (data != '') {
                            window.location.href = '${root}' + data;
                        }
                    },
                    error : function(xhr, status, err) {
                        alert("Authentication failure: " + err);
                    }
                });
            },
            onlogout : function() {
                //window.location.href = "${root}/logout";
            }
        });
    });
</script>

<c:if test="${!userLoggedIn}">
 <script type="text/javascript">
     $(document).ready(function() {
         var signinLink = $("#personaSignin");
         signinLink.click(function() {
             userRequestedAuthentication = true;
             navigator.id.request({siteName: 'SaltedHashed'});
         });
     });
 </script>
</c:if>

<jsp:invoke fragment="header"/>

</head>

<body>

    <div class="container">

        <div class="masthead" style="margin-bottom: 25px;">
            <h3 class="text-muted">SaltedHashed</h3>
            <ul class="nav nav-justified">
                <li<c:if test="${currentPage == 'home'}"> class="active"</c:if>><a href="${root}/">Home</a></li>
                <c:if test="${userLoggedIn}">
                    <li<c:if test="${currentPage == 'sites'}"> class="active"</c:if>><a href="${root}/sites">My sites</a></li>
                </c:if>
                <li<c:if test="${currentPage == 'docs'}"> class="active"</c:if>><a href="${root}/docs">Docs</a></li>
                <li<c:if test="${currentPage == 'about'}"> class="active"</c:if>><a href="${root}/about">About</a></li>

                <c:if test="${!userLoggedIn}">
                    <li><a href="javascript:void(0);" id="personaSignin">Sign in</a></li>
                </c:if>
                <c:if test="${userLoggedIn}">
                    <li><a href="${root}/logout">Logout</a></li>
                </c:if>
            </ul>
        </div>

        <c:if test="${!empty param.message}">
            <div style="color: green; text-align: center; font-size: 15pt;">${param.message}</div>
        </c:if>

<jsp:doBody />

        <!-- Site footer -->
        <div class="footer">
            <p></p>
        </div>

    </div>
    <!-- /container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
