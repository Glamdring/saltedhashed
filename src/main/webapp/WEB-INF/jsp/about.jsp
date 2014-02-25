<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes.jsp"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<c:set var="currentPage" value="about" scope="request" />

<t:template>
    <div class="lead">
        SaltedHashed is a project by <a
            href="mailto:bozhidar.bozhanov@gmail.com">Bozhidar Bozhanov</a>.
        Users should not suffer from poorly created websites, and that's why
        we should demand high standards from everyone on the web. This
        project is an attempt towards that goal. The <a
            href="https://github.com/Glamdring/saltedhashed">source code can
            be found at GitHub</a>.
    </div>
</t:template>