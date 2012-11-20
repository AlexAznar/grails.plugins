package shop

class Product {

    String title
    String description
    String longDescription
    BigDecimal price
    Type type = Type.STANDARD

    // display
    String template

    static transients = ['accessoire','recommendations']
    
    def getRecommendations() {
        links.findAll { it.type == ProductLink.LinkType.RECOMMENDATION }
    }
    def getAccessoire() {
        links.findAll { it.type == ProductLink.LinkType.ACCESSOIRE }
    }


    static hasMany = [links: ProductLink]
    static mappedBy = [outboundLinks: "from",links:'to']
    
    static constraints = {
        longDescription nullable: true
        template nullable:true
        links nullable: true
    }
    
    static mapping = {
        type enumType: "ordinal"
    }
    
    enum Type {
        STANDARD,
        GROUP,
        SUBPRODUCT,
        CONFIGURABLE
    }
}
