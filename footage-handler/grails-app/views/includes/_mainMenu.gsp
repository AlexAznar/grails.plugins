<g:if test="${page != 'upload'}">
  <g:link controller="upload" action="index">Upload image</g:link>
  <br/>
</g:if>

<g:if test="${page != 'list'}">
  <g:link controller="imageFile" action="list">Images list</g:link>
</g:if>