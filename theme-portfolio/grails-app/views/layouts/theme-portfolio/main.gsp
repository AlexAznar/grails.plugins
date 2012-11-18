<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="DBrails App"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">    
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <r:require modules="themePortfolio,chosen"/>
    <g:layoutHead/>
    <r:layoutResources />
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800' rel='stylesheet' type='text/css'>
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
  </head>

  <body>

  	<!-- Header
  	================================================== -->
  	<header id="header">

  		<!-- Navigation
  		================================================== -->
  		<div class="navbar">
  			<div class="navbar-inner">
  				<div class="container">
  					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
  						<span class="icon-bar"></span>
  						<span class="icon-bar"></span>
  						<span class="icon-bar"></span>
  					</a>
  					<a class="brand" href="index.html">
  						<g:layoutTitle default="Portfolio"/>
  					</a>
  					<div class="nav-collapse fr">
  						<ul class="nav">
  						    <g:each in="${pages}" var="page">
  						        <li class="${page.name=='home'?'active':''}"><a href="${createLink(controller:'pages',action:page.name)}">${page.title}</a></li>
  						    </g:each>
  							<li><button class="btn btn-primary" onclick="window.location.href='${createLink(controller:'app',action:'index')}'">Sign In</button></li>
  						</ul>
  					</div><!--/.nav-collapse -->
  				</div><!-- end .container -->
  			</div><!-- end .navbar-inner -->
  		</div><!-- end .navbar -->
    </header>

	<!-- Content
	================================================== -->
	<div id="content" class="container">
        <g:layoutBody/>
	</div>

	<footer class="row">
		<div class="container">
			<div class="row">
				<div class="span2">
					<h3>Quick links</h3>
					<ul>
						<li><a href="#" title="">Home</a></li>
						<li><a href="#" title="">Our work</a></li>
						<li><a href="#" title="">About us</a></li>
					</ul>
				</div>
				<div class="span2">
					<br /><br />
					<ul>
						<li><a href="#" title="">Pricing</a></li>
						<li><a href="#" title="">Explore</a></li>
						<li><a href="#" title="">Contact</a></li>
					</ul>
				</div>
				<div class="span4 social-networks">
					<h3>Stay in touch</h3>
					<p>Stay in touch on social networks</p>
					<a href="#" title="Follow us on Facebook" class="icon-facebook"></a>
					<a href="#" title="Follow us on Twitter" class="icon-twitter"></a>
					<a href="#" title="Follow us on Google Plus" class="icon-google-plus"></a>
					<a href="#" title="Follow us on Google Plus" class="icon-github"></a>
					<a href="#" title="Follow us on Google Plus" class="icon-pinterest"></a>
				</div>
				<div class="span4">
					<h3>Newsletter</h3>
					<p>Subscribe to our monthly newsletter and be the first to know about our news and special deals!</p>
					<form>
						<input type="text" placeholder="Enter your E-mail" />
						<input type="submit" class="btn" value="Ok" />
					</form>
				</div>
			</div> <!-- /row -->		
		</div> <!-- /container -->

    	<div id="footer-extra">
    		<div class="container">
    			<div class="row">
    				<div class="span4">
    					&copy; 2010—2012 Your company
    				</div>
    				<div class="span4">
    					<i class="icon-phone"></i> &nbsp; 
    					00123 320 000 123 456
    				</div>
    				<div class="span4">
    					<i class="icon-envelope"></i> &nbsp; 
    					hello@yourcomapny.com
    				</div>
    			</div> <!-- end .row -->		
    		</div> <!-- end .container -->		
    	</div>

    </footer>

    <!-- Placed at the end of the document so the pages load faster -->
    <r:layoutResources />
  </body>
</html>
