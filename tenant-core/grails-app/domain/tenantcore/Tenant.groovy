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


	def retrieveAttributeList( String type ) {
		return Tenant.GenericGetAttributeList( this, type )
	}
	def storeAttributeList( String type, def attrs ) {
		Tenant.GenericSetAttributeList( this, type, attrs )		
	}

	def retrieveAttributeMap( String type ) {
			return Tenant.GenericGetAttributeMap( this, type )		
	}
	def storeAttributeMap( String type, def attrs ) {
		Tenant.GenericSetAttributeMap( this, type, attrs )		
	}

	// magic set
	def propertyMissing(String name, value) {
		String kind = name.endsWith('List') ? 'List' : name.endsWith('Map') ? 'Map' : null
		if( !kind )
			throw Exception("Property ${name} not found for class Tenant")
		
		String type = name.substring( 0, name.indexOf(kind) )

		if( kind == 'List' ) {
			Tenant.GenericSetAttributeList( this, type, value )
		}
		else if( kind == 'Map' ) {
			Tenant.GenericSetAttributeMap( this, type, value )
		}
	}

	// magic get
	def propertyMissing(String name) {
		String kind = name.endsWith('List') ? 'List' : name.endsWith('Map') ? 'Map' : null
		if( !kind )
			throw Exception("Property ${name} not found for class Tenant")

		String type = name.substring( 0, name.indexOf(kind) )

		if( kind == 'List' ) {
			return Tenant.GenericGetAttributeList( this, type )
		}
		else if( kind == 'Map' ) {
			return Tenant.GenericGetAttributeMap( this, type )
		}	
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
