<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Simple GSP page</title>
  <uploader:head/>
  </head>
  <body>
  <g:render template="/includes/mainMenu" model="['page': 'upload']"/>
  <g:form action="upload">
    <g:renderErrors bean="${command}" field="errorField"/>
    <uploader:uploader id="picture" url="${createLink(controller:'upload', action:'upload')}"/>
  </g:form>
  </body>
</html>