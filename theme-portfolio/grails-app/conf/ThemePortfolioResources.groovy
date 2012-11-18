modules = {
    themePortfolio {
        dependsOn 'jquery', 'bootstrap'

        resource url:'theme-portfolio/js/jquery.isotope.min.js'
        resource url:'theme-portfolio/js/jquery.touchSwipe.js'
        resource url:'theme-portfolio/js/functions.js'

        resource url:'theme-portfolio/css/style.uncompressed.css'
        resource url:'theme-portfolio/css/font-awesome/font-awesome.css'
        resource url:'theme-portfolio/css/font-awesome/font-awesome-ie7.css'
// todo:        <!--[if IE 7]>
//    		<link href="css/font-awesome/font-awesome-ie7.css" rel="stylesheet">
//    	<![endif]-->
    }
}