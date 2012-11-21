package tenantcore

/**
 * Finds Tenant for every request
 **/
class TenantFilters {
	def tenantResolverService

    def filters = {
        all(controller:'*', action:'*') {
            before = {
				// find subdomain
				int dotIndex = request.serverName.indexOf(".")
		        if( dotIndex > 0 ) {
					// and set subdomain in request
		            request.subdomain = request.serverName.substring( 0, dotIndex )
		        }
				else {
					request.subdomain = null
				}

				// resolve tenant with overrideable tenent resolver
				// and set in request
				def t = tenantResolverService.resolveTenant( request )
                request.tenant = t ?: tenantResolverService.resolveDefaultTenant( request )

				println "tenant ${request.tenant.name}"
            }
            after = { Map model ->
            }
            afterView = { Exception e ->

            }
        }
    }
}
