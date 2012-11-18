<html>
<head>
    <title>${page.title}</title>
    <meta name="layout" content="${page.layout}"/>
</head>
<body>
<g:each in="${page.getContent()}" var="content"><g:if test="${content.locale == org.springframework.web.servlet.support.RequestContextUtils.getLocale(request).language.toLowerCase()+"_"+org.springframework.web.servlet.support.RequestContextUtils.getLocale(request).country.toUpperCase()}"
><g:if test="${content.controller}">${g.include(controller:content.controller,action:content.action)}</g:if><g:else><g:if test="${content.template}">${g.render(template:content.template)}</g:if><g:else>${content.content}</g:else></g:else></g:if>
</g:each>
</body>
</html>