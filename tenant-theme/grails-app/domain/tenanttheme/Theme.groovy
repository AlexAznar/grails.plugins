package tenanttheme

import tenantcore.*


class Theme {

	Tenant tenant
	String name

    static constraints = {
		
		name unique: true
    }
}
