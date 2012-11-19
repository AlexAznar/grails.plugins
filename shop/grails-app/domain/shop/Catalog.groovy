package shop

class Catalog {

    String name
    static hasMany = [products: Product]

    static constraints = {
    }
}
