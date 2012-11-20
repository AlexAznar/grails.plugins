package tenantcore

class TenantResolverService {
	
	def tenantService

    public Tenant resolveTenant( def request ) {
		return tenantService.resolveTenantByDefaultImplementation( request )
    }

	public Tenant resolveDefaultTenant( def request ) {
		return tenantService.resolveDefaultTenantByDefaultImplementation( request )
	}

}
