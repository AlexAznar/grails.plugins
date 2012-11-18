<meta name="layout" content="main" />
<g:form action="savePicture" enctype="multipart/form-data">
    <g:renderErrors bean="${useInstance}" field="name" />     
    <input name="picture" type="file" />
    <g:submitButton name="submit" value="Submit"/>
</g:form>