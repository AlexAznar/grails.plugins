<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
</head>
    <body>
        <g:each in="${ogoneMap}" var="payment">
            <form action="${url}" method="POST">
                <g:each in="${payment.value.ogone}">
                    <input type="hidden" name="${it.key}" value="${it.value}">
                </g:each>

                <input type="hidden" name="SHASign" value="${payment.value.hash}">
                <input type="submit" value="submit ${payment.key}">
            </form>
        </g:each>
    </body>
</html>