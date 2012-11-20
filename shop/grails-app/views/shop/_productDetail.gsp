<h3>${product.title}</h3>
<b>${product.price}</b>
<p>${product.description}</p>
<g:if test="${product.links}">
<b>Links</b>
<g:each in="${product.links}" var="link">
    <p>${link.from.title} <i>is</i> <b>${link.type}</b> <i>of</i> ${link.to.title}</p>
</g:each>
</g:if>
<hr/>