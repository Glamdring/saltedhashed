    <a class="btn btn-lg btn-success" href="javascript:openSiteDialog()" role="button">New site</a>
        <div style="display: none;" id="siteForm">
            <form action="${root}/site/save" method="POST">
                <div class="form-row"><label>Base URL (e.g. http://yoursite.com)</label>
                <input type="text" name="baseUrl" id="baseUrlInput" size="30" />
                </div>

                <div class="form-row"><label>Endpoint path (e.g. /api/verify-passwords)</label>
                <input type="text" name="endpointPath" id="endpointPathInput" size="30" />
                </div>

                <div class="form-row" style="margin-top: 5px;">
                   <input type="submit" class="btn btn-lg btn-success" role="button" value="Save" />
                </div>
            </form>
        </div>
        <table class="table table-bordered table-striped" style="margin-top: 5px;">
            <thead>
                <tr>
                    <th>Base URL</th>
                    <th>Endpoint path</th>
                    <th>Latest status</th>
                    <th style="width: 240px;">Badge code</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sites}" var="site" varStatus="stats">
                    <tr>
                        <td>${site.baseUrl}</td>
                        <td>${site.endpointPath}</td>
                        <td>${site.statuses.isEmpty() ? "N/A" : site.statuses.get(0).success ? '<span class"success">OK</span>' : site.statuses.get(0).message}</td>
                        <td>
                            <c:if test="${!site.statuses.isEmpty() and site.statuses.get(0).success}">
                                <textarea rows="3" cols="40" style="font-size: 9px;"><a href="${baseUrl}/${root}/site/status?baseUrl=${site.baseUrl}"><img src="${baseUrl}/${staticRoot}/img/badge.png" style="border-style: none;"/></a></textarea>
                            </c:if>
                            <c:if test="${site.statuses.isEmpty() or !site.statuses.get(0).success}">
                                Not availale if status is not "OK"
                            </c:if>
                        </td>
                        <td><a href="javascript:edit('${site.baseUrl}', '${site.endpointPath}');">Edit</a></td>
                        <td><a onclick="if (confirm('Are you sure you want to delete this site?')) {return true;} else {return false;}" href="${root}/site/delete?baseUrl=${site.baseUrl}">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
