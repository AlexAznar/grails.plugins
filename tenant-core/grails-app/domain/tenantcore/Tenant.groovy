package tenantcore

class Tenant {

	String name
    List<TenantAttribute> attributes

	static hasMany = [attributes:TenantAttribute]

    static constraints = {
		name unique: true, blank: false
		attributes nullable: true
    }


    static transients = ['config','domains']

	def getConfig() {
		return Tenant.GenericGetAttributeMap( this, 'config' )
	}

	def setConfig( def config = [:] ) {
		Tenant.GenericSetAttributeMap( this, 'config', config )
	}


	def getDomains() {
		return Tenant.GenericGetAttributeList( this, 'domain' )
	}

	def setDomains( def domains ) {
		Tenant.GenericSetAttributeList( this, 'domain', domains )
	}


	// private helper closures
	private final static def GenericGetAttributeMap = { Tenant self, String type ->
		def items = self.attributes?.findAll { it.type == type }

		def c = [:]
		for( i in items ) {
			c."${i.name}" = i.value
		}
	
		return c
	}

    private final static def GenericSetAttributeMap = { Tenant self, String type, attrs ->
        attrs.each { n, v ->
            def a = self.attributes?.find { it.type == type && it.name == n }
            if( a ) {
                if( v )
                    a.value = v
                else
                    a.delete()
            }
            else
                self.addToAttributes new TenantAttribute( name: n, value:v, type: type )
        }
    }


    private final static def GenericGetAttributeList = { Tenant self, String type ->
        def items = self.attributes?.findAll {
            it.type == type
        }

        def c = []
        for( i in items ) {
            c << i.name
        }

        return c
    }

    private final static def GenericSetAttributeList = { Tenant self, String type, attrs ->
        for( v in attrs ) {
            def a = self.attributes?.find { it.type == type && it.name == v }
            if( a ) {
                if( v )
                    a.name = v
                else
                    a.delete()
            }
            else
                self.addToAttributes new TenantAttribute( name: v, value:null, type: type )
        }
    }
}
