<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template>
    <jsp:attribute name="header">
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />

        <script type="text/javascript">
            function openSiteDialog() {
                $("#baseUrlInput").removeAttr("readonly");
                $("#siteForm").dialog();
            }
            function edit(baseUrl, endpointPath) {
                $("#siteForm").dialog();
                $("#baseUrlInput").val(baseUrl);
                $("#baseUrlInput").attr("readonly", "readonly");
                $("#endpointPathInput").val(endpointPath);
            }
        </script>
    </jsp:attribute>

    <jsp:body>
        <a class="btn btn-lg btn-success" href="javascript:openSiteDialog()" role="button">New website</a>
        <div style="display: none;" id="siteForm">
            <form action="${root}/site/save" method="POST">
                <div class="form-row"><label>Base URL (e.g. http://yoursite.com)</label>
                <input type="text" name="baseUrl" id="baseUrlInput" />
                </div>

                <div class="form-row"><label>Endpoint path (e.g. /api/verify-passwords)</label>
                <input type="text" name="endpointPath" id="endpointPathInput" />
                </div>

                <div class="form-row">
                   <input type="submit" class="btn btn-lg btn-success" role="button" value="Save" />
                </div>
            </form>
        </div>
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Base URL</th>
                    <th>Endpoint path</th>
                    <th>Latest status</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sites}" var="site" varStatus="stats">
                    <tr>
                        <td>${site.baseUrl}</td>
                        <td>${site.endpointPath}</td>
                        <td>${site.statuses.isEmpty() ? "" : site.statuses.get(0).success ? '<span class"success">OK</span>' : site.statuses.get(0).message}</td>
                        <td><a href="javascript:edit('${site.baseUrl}', '${site.endpointPath}');">Edit</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:template>