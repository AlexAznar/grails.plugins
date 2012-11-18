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
    <r:require modules="themeBaseAdmin,app,chosen"/>
    <g:layoutHead/>
    <r:layoutResources />
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600" rel="stylesheet">
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
  </head>

<body>
    <div class="navbar navbar-fixed-top">

    	<div class="navbar-inner">

    		<div class="container">

    			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
    				<span class="icon-bar"></span>
    				<span class="icon-bar"></span>
    				<span class="icon-bar"></span>
    			</a>

    			<a class="brand" href="./">
    				<g:layoutTitle default="Base Admin"/>
    			</a>		

    			<div class="nav-collapse">
    				<ul class="nav pull-right">
    					<li class="dropdown">

    						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
    							<i class="icon-cog"></i>
    							Settings
    							<b class="caret"></b>
    						</a>

    						<ul class="dropdown-menu">
    							<li><a href="javascript:;">Account Settings</a></li>
    							<li><a href="javascript:;">Privacy Settings</a></li>
    							<li class="divider"></li>
    							<li><a href="javascript:;">Help</a></li>
    						</ul>

    					</li>

    					<li class="dropdown">

    						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
    							<i class="icon-user"></i> 
    							Rod Howard
    							<b class="caret"></b>
    						</a>

    						<ul class="dropdown-menu">
    							<li><a href="javascript:;">My Profile</a></li>
    							<li><a href="javascript:;">My Groups</a></li>
    							<li class="divider"></li>
    							<li><a href="javascript:;">Logout</a></li>
    						</ul>

    					</li>
    				</ul>

    				<form class="navbar-search pull-right">
    					<input type="text" class="search-query" placeholder="Search">
    				</form>

    			</div><!--/.nav-collapse -->	

    		</div> <!-- /container -->

    	</div> <!-- /navbar-inner -->

    </div> <!-- /navbar -->
<div class="subnavbar" style="margin-top:-18px">

	<div class="subnavbar-inner">

	
		<div class="container">
			<ul class="mainnav pull-left">
			
				<li class="active">
                    <div class="btn-toolbar" style="margin-top: 18px;">
                            <div class="btn-group">
                                <select data-placeholder="Database Working-Set" style="width:150px;" class="chzn-select" multiple="multiple" tabindex="6">
                                  <option value=""></option>
                                  <optgroup label="scharfmedia">
                                    <option>mysqllocal</option>
                                    <option>sql prod</option>
                                  </optgroup>
                                  <optgroup label="megusta">
                                    <option>chat live</option>
                                    <option>chat dev</option>
                                    <option>another project</option>
                                  </optgroup>
                                  <optgroup label="customer1">
                                    <option>gaming db</option>
                                  </optgroup>
                                </select>
                                <!--
                              <button class="btn btn-large dropdown-toggle btn-inverse" data-toggle="dropdown">Select Database<span class="caret"></span></button>
                              <ul class="dropdown-menu">
                                <li><a href="#">mysqllocal</a></li>
                                <li><a href="#">Another db</a></li>
                                <li><a href="#">oracle database</a></li>
                                <li class="divider"></li>
                                <li><a href="#">new connection</a></li>
                              </ul>
                              -->
                            </div><!-- /btn-group -->
                            
                          </div>
				</li>
				<li class="">
					<a href="./">
						<i class="icon-plus"></i>
						<span>New Connection</span>
					</a>	    				
				</li>
			</ul>
			
			<ul class="mainnav pull-right">
			
				<li class="active">
					<a href="${createLink(view:'/')}">
						<i class="icon-home"></i>
						<span>Dashboard</span>
					</a>	    				
				</li>
				
				<li>
					<a href="./faq.html">
						<i class="icon-pushpin"></i>
						<span>Server</span>
					</a>	    				
				</li>
				
				<li>					
					<a href="${createLink(controller:'app',action:'index')}" class="dropdown-toggle">
						<i class="icon-th-large"></i>
						<span>Tables</span>
					</a>	  				
				</li>
				
				<li>
					<a href="./reports.html">
						<i class="icon-bar-chart"></i>
						<span>Views</span>
					</a>    				
				</li>
				
				<li>					
					<a href="${createLink(controller:'test',action:'query')}">
						<i class="icon-facetime-video"></i>
						<span>Queries</span>
					</a>  									
				</li>
				
				<li class="dropdown">					
					<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-share-alt"></i>
						<span>More Pages</span>
						<b class="caret"></b>
					</a>	
				
					<ul class="dropdown-menu">
						<li><a href="./charts.html">Charts</a></li>
						<li><a href="./account.html">User Account</a></li>
						<li class="divider"></li>
						<li><a href="./login.html">Login</a></li>
						<li><a href="./signup.html">Signup</a></li>
						<li><a href="./error.html">Error</a></li>
					</ul>    				
				</li>
			
			</ul>

		</div> <!-- /container -->
	
	</div> <!-- /subnavbar-inner -->

</div> <!-- /subnavbar -->
    

<div class="main">
	
	<div class="main-inner">

	    <div class="container">

            <g:layoutBody/>
	
	    </div> <!-- /container -->
	    
	</div> <!-- /main-inner -->
    
</div> <!-- /main -->
    


<div class="extra">

	<div class="extra-inner">

		<div class="container">

			<div class="row">
				
    			<div class="span3">
    				
    				<h4>About</h4>
    				
    				<ul>
    					<li><a href="javascript:;">About Us</a></li>
    					<li><a href="javascript:;">Twitter</a></li>
    					<li><a href="javascript:;">Facebook</a></li>
    					<li><a href="javascript:;">Google+</a></li>
    				</ul>
    				
    			</div> <!-- /span3 -->
    			
    			<div class="span3">
    				
    				<h4>Support</h4>
    				
    				<ul>
    					<li><a href="javascript:;">Frequently Asked Questions</a></li>
    					<li><a href="javascript:;">Ask a Question</a></li>
    					<li><a href="javascript:;">Video Tutorial</a></li>
    					<li><a href="javascript:;">Feedback</a></li>
    				</ul>
    				
    			</div> <!-- /span3 -->
    			
    			<div class="span3">
    				
    				<h4>Legal</h4>
    				
    				<ul>
    					<li><a href="javascript:;">License</a></li>
    					<li><a href="javascript:;">Terms of Use</a></li>
    					<li><a href="javascript:;">Privacy Policy</a></li>
    					<li><a href="javascript:;">Security</a></li>
    				</ul>
    				
    			</div> <!-- /span3 -->
    			
    			<div class="span3">
    				
    				<h4>Settings</h4>
    				
    				<ul>
    					<li><a href="javascript:;">Consectetur adipisicing</a></li>
    					<li><a href="javascript:;">Eiusmod tempor </a></li>
    					<li><a href="javascript:;">Fugiat nulla pariatur</a></li>
    					<li><a href="javascript:;">Officia deserunt</a></li>
    				</ul>
    				
    			</div> <!-- /span3 -->
    			
    		</div> <!-- /row -->

		</div> <!-- /container -->

	</div> <!-- /extra-inner -->

</div> <!-- /extra -->


    
    
<div class="footer">
	
	<div class="footer-inner">
		
		<div class="container">
			
			<div class="row">
				
    			<div class="span12">
    				&copy; 2012 <a href="http://bootstrapadmin.com">Base Admin</a>.
    			</div> <!-- /span12 -->
    			
    		</div> <!-- /row -->
    		
		</div> <!-- /container -->
		
	</div> <!-- /footer-inner -->
	
</div> <!-- /footer -->
    

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<r:layoutResources />

  </body>
</html>
