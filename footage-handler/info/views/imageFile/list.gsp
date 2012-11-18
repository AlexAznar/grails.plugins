<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Footage plugin demo</title>
</head>
<body>
<g:render template="/includes/mainMenu" model="['page': 'list']"/>
<table>
  <thead>
    <th>Image id</th>
    <th>Edit</th>
    <th>Remove</th>
  </thead>
  <tbody>
    <g:each in="${images}" status="i" var="image">
      <tr>
        <td>${image.id}</td>
        <td><g:link controller="imageFile" action="edit" id="${image.id}">Edit</g:link></td>
        <td><g:link controller="imageFile" action="remove" id="${image.id}">Remove</g:link></td>
      </tr>
    </g:each>
  </tbody>
</table>
</body>
</html>