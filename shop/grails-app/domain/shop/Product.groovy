package shop

class Product {

    String title
    String description
    BigDecimal price
    ProductType type

    // display
    String template
    
    static constraints = {
        template nullable:true
    }
}
