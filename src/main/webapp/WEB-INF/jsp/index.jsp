<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <!-- Jumbotron -->

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
            <h1>Prove to your users that you store passwords securely</h1>
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

        <!-- Example row of columns -->
        <div class="row">
            <div class="col-lg-4">
                <h2>Safari bug warning!</h2>
                <p class="text-danger">As of v7.0.1, Safari exhibits a bug in
                    which resizing your browser horizontally causes rendering errors in
                    the justified nav that are cleared upon refreshing.</p>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce
                    dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
                    ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
                    magna mollis euismod. Donec sed odio dui.</p>
                <p>
                    <a class="btn btn-primary" href="#" role="button">View details
                        &raquo;</a>
                </p>
            </div>
            <div class="col-lg-4">
                <h2>Heading</h2>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce
                    dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
                    ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
                    magna mollis euismod. Donec sed odio dui.</p>
                <p>
                    <a class="btn btn-primary" href="#" role="button">View details
                        &raquo;</a>
                </p>
            </div>
            <div class="col-lg-4">
                <h2>Heading</h2>
                <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
                    egestas eget quam. Vestibulum id ligula porta felis euismod semper.
                    Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
                    nibh, ut fermentum massa.</p>
                <p>
                    <a class="btn btn-primary" href="#" role="button">View details
                        &raquo;</a>
                </p>
            </div>
        </div>
</t:template>