package shop

class ShopService {

    def createTestdata() {
        def type = new ProductType( name: 'default' )
        type.save()

        def catalog = new Catalog( name:'default catalog' )
        def p1 = new Product( price: 1000.00, title: 'Samsung TV', description: 'a nice tv', type: type )
        catalog.addToProducts( p1 )
        def p2 = new Product( price: 500.00, title: 'Wasserhahn', description: 'premium tap', type: type )
        catalog.addToProducts( p2 )
        catalog.save()
    }
}
