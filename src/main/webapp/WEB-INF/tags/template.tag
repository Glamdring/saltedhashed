<%@tag description="Main template" pageEncoding="UTF-8"%>

<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set value="${pageContext.request.contextPath}/static" var="staticRoot" />
<c:set value="${pageContext.request.contextPath}" var="root" />

<c:if test="${root == '//'}">
    <c:set value="" var="root" />
    <c:set value="/static" var="staticRoot" />
</c:if>

<c:set var="userLoggedIn" value="${userContext.user != null}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../assets/ico/favicon.ico">

<title>SaltedHahshed - prove that your sites store passwords properly</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
    href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
    href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
    src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

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
</head>

<body>

    <div class="container">

        <div class="masthead">
            <h3 class="text-muted">SaltedHashed</h3>
            <ul class="nav nav-justified">
                <li class="active"><a href="${root}">Home</a></li>
                <li><a href="javascript:void(0);" id="personaSignin">Sign in</a></li>
                <li><a href="#">Contact</a></li>
            </ul>
        </div>


<jsp:doBody />

        <!-- Site footer -->
        <div class="footer">
            <p>&copy; Company 2014</p>
        </div>

    </div>
    <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
</body>
</html>