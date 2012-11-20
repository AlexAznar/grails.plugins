package tenantcore

class TenantAttribute {

	Tenant tenant
	String type // e.g. config
	String name
	String value
	
	static belongsTo = [tenant:Tenant]

    static constraints = {
		id composite: ['tenant','type','name']
        value nullable: true, blank: false
    }
}
