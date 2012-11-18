<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
</head>
    <body>
        <form method="POST" action="${url}">
            <g:each in="${ogone}">
                <input type="hidden" name="${it.key}" value="${it.value}">
            </g:each>

            <input type="hidden" name="SHASign" value="${ogoneHash}">
            <input type="submit" value="submit">
        </form>
    
    </body>
</html>