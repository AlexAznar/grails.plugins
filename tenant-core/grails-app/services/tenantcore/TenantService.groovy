package tenantcore

class TenantService {

    // injected properties
    def grailsApplication


	/**
	 * resolveTenant
	 *
	 * find Tenant by subdomain-to-tenanteName-compare first and if nothing found
	 * by complete domain lookup
	 *
	 * NOTE: you probably want to create a custom tenant service in your application and
	 *       and override this method
	 **/
	public Tenant resolveTenantByDefaultImplementation( def request ) {

        def tenant = null

        // find by subdomain
        if( request.subdomain )
            tenant = Tenant.findByName( request.subdomain )

        // find by serverName
        tenant = Tenant.findByName( request.serverName )

        if( !tenant ) {
			// find by domain a.k.a hostname
			def a = TenantAttribute.findByTypeAndName( 'domain', request.serverName )
			tenant = a?.tenant
		}
		
		return tenant
	}
	
	/**
	 * resolveDefaultTenant
	 * we found no tenant by 'resolveTenant' method and provide a fallback mechanism.
	 * per default this tries to find a 'default' Tenant by name and if not found
	 * it does create the 'default' Tenant
	 *
	 * NOTE: if you have a custom default Tenant logic you want to override this
	 *       in a custom tenantservice implementation in your app
	 **/
	public Tenant resolveDefaultTenantByDefaultImplementation( def request ) {
		def t = Tenant.findByName( 'default' )
		// if we have no default tenant, create one now
		if( !t ) {
			t = new Tenant( name: 'default' )
			t.save(flush:true)
		}
		
		return t
	}

    /**
     * initializeWithConfig
     *
     * takes a configuration Map (or the config) which is used to fill the database with tenants.
     * it checks if the tenant already exists but in any case overrides the old attributes with the new.
     *
     * @param tenants
     */
    public void initializeWithConfig( def tenants = null ) {
        // if no config is supplied try to get it from the app config
        if( !tenants ) {
            tenants = grailsApplication.config.grails?.plugins?.tenantcore?.tenants
        }

        // iterate over every tenant and put it into database
        tenants?.each { String name, attrs ->
            def t = Tenant.findByName( name )
            t = t ?: new Tenant( name: name ).save()

            if( attrs?.config )
                t.config = attrs.config
            if( attrs?.domains )
                t.domains = attrs.domains

            t.save( flush: true )
        }
    }
}
