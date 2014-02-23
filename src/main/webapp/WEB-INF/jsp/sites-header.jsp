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