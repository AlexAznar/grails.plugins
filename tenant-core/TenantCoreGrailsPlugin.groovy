class TenantCoreGrailsPlugin {
    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Tenant Core Plugin" // Headline display name of the plugin
    def author = "Christoph Scharf"
    def authorEmail = "christoph.scharf@scharfmedia.de"
    def description = '''\
Enables simple multi tenancy by filtering web requests and setting the request.tenant property.
You can put attributes to Tenants and also address multiple subdomains and domains.

Example Configuration Entry:
// tenantcore plugin
grails.plugins.tenantcore.tenants = [
    'alpha':   [config:[mobile:'123',address:'Greylsstreet 17'],    domains:['www.alpha-grails.com','my-grails.net']],
    'charlie': [config:[mobile:'456',address:'LeGrey LS Square 1'], domains:['www.grails.com','charlie.grails.com']]
]

You can provide your own TenantResolverService as SpringBean 'tenantResolverService' with custom logic how to extract the Tenant from the request.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/tenant-core"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
        def tenantService = applicationContext.getBean('tenantService')
        tenantService.initializeWithConfig()
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
        def tenantService = applicationContext.getBean('tenantService')
        tenantService.initializeWithConfig()
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
