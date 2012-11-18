<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>
    Footage handler plugin demo
  </title>
  <g:javascript src="jquery.min.js"/>
  <g:javascript src="jquery.Jcrop.js"/>
  <link rel="stylesheet" href="${resource(dir: 'css/', file: 'jquery.Jcrop.css')}" type="text/css"/>
</head>
<body>
<g:render template="/includes/mainMenu"/>
<g:form action="cropAction">
  <g:hiddenField name="id" value="${id}"/>
  <img src="${createLink(controller:'imageFile', action:'showImage', id: id)}" id="target" alt="Sample"/>
  <g:hiddenField name="x" id="x"/>
  <g:hiddenField name="y" id="y"/>
  <g:hiddenField name="x2" id="x2"/>
  <g:hiddenField name="y2" id="y2"/>
  <p>${editMessage}</p>
  <g:submitButton name="crop" value="Crop"/>
</g:form>
<br>
<g:form action="adjust">
  <g:hiddenField name="id" value="${id}"/>
  Profile Width:
  <g:textField name="width" size="100px"/>
  <br>
  Profile Height:
  <g:textField name="height" size="100px"/>
  <g:submitButton name="adjust" value="Adjust Image"/>
</g:form>

<script type="text/javascript">
  jQuery(function($) {
    $('#target').Jcrop({
      onChange: setCoords
    });
  });

  function setCoords(c) {
    $('#x').val(c.x);
    $('#y').val(c.y);
    $('#x2').val(c.x2);
    $('#y2').val(c.y2);
  }
</script>
</body>
</html>