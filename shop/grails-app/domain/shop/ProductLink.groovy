package shop

class ProductLink {

    Product from
    Product to
    LinkType type
    
    static belongsTo = [from:Product,to:Product]

    static constraints = {
    }

    static mapping = {
        type enumType: "ordinal"
    }
    
    enum LinkType {
        SUBPRODUCT,
        ACCESSOIRE,
        RECOMMENDATION
    }
}
