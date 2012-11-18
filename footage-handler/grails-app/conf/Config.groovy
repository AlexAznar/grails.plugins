// configuration for plugin testing - will not be included in the plugin zip
grails.app.context = '/'
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    warn 'org.mortbay.log'
}

//added by the footage-handler plugin'
//feel free to implement unlimited different restrictions
//replace the value "default" in the field extensions
environments {
    production {

    }
    development {
        grails.plugins.megusta.footage.handler.storage.default = "s3" //local or s3
        grails.plugins.megusta.footage.handler.required.default = true
        grails.plugins.megusta.footage.handler.subdirectory.default = "streams"
        grails.plugins.megusta.footage.handler.extensions.default = ["jpg", "png", "img"]
        grails.plugins.megusta.footage.handler.maxFileSize.default = 524288 * 2 // in bytes
        grails.plugins.megusta.footage.handler.tempDir = '/tmp'

        grails.plugins.megusta.footage.handler.bucket.default = 'mdh-recordings'
        grails.plugins.megusta.footage.handler.bucketLink.default = "https://s3.amazonaws.com/mdh-recordings"

        grails.plugin.aws.accessKey = ''
        grails.plugin.aws.secretKey = ''
        grails.plugin.aws.bucket    = ''
        grails.plugin.aws.serverUrl = ''
        grails.plugin.aws.bucketLink= ''


        //Demo profile defaults
        grails.plugin.footagehandler.default.profile.name = "defaultProfile"
        grails.plugin.footagehandler.default.profile.maxHorizontalCropping = 20
        grails.plugin.footagehandler.default.profile.maxVerticalCropping = 20
        grails.plugin.footagehandler.default.profile.maxHorizontalScaling = 20
        grails.plugin.footagehandler.default.profile.maxVerticalScaling = 20
        grails.plugin.footagehandler.default.profile.targetWidth = 500
        grails.plugin.footagehandler.default.profile.targetHeight = 500
    }
    test { }
}


grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"