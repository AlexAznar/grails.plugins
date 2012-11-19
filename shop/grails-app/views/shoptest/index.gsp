<html>
<head>
  <meta content="main" name="layout">
</head>
<body>
    <h2>Catalog: ${catalog.name}</h2>

    Products
    <g:render template="/shop/productList" model="[products:catalog.products]"/>

    <hr/>
    <g:each in="${catalog.products}" var="product">
        <g:render template="/shop/productDetail" model="[product:product]"/>
    </g:each>
</body>
</html>