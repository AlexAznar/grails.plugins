package tenanttheme

class Theme {

	String name

    static constraints = {
		name unique: true
    }
}
