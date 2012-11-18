<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Simple GSP page</title>
  <uploader:head/>
  <!-- jsProgressBarHandler prerequisites : prototype.js -->
  <script type="text/javascript" src="/js/prototype/prototype.js"></script>
  <!-- jsProgressBarHandler core -->
  <script type="text/javascript" src="/js/bramus/jsProgressBarHandler.js"></script>
</head>
<body>
<g:render template="/includes/mainMenu" model="['page': 'upload']"/>
<g:form action="upload">
  <g:renderErrors bean="${command}" field="errorField"/>
  <uploader:uploader id="picture" url="${createLink(controller:'upload', action:'upload')}" multiple="false"
          allowedExtensions="${[allowedExtensions]}" sizeLimit="${maxFileSize}">
    <uploader:onComplete>responseJSON</uploader:onComplete>
  </uploader:uploader>
</g:form>
</body>
</html>