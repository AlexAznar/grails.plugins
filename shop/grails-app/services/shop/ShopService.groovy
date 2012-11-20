package shop

class ShopService {

    def createTestdata() {
        
        def catalog = new Catalog( name:'default catalog' )
        def p1 = new Product( price: 1000.00, title: 'Samsung TV', description: 'a nice tv')
        catalog.addToProducts( p1 )
        def p2 = new Product( price: 500.00, title: 'Wasserhahn', description: 'premium tap')
        catalog.addToProducts( p2 )
        def p3 = new Product( price: 19.95, title: 'Coconut', description: 'really delicious')
        catalog.addToProducts( p3 )
        catalog.save()
        
        new ProductLink( from: p2, to: p1, type: ProductLink.LinkType.ACCESSOIRE ).save(flush:true)
        new ProductLink( from: p1, to: p2, type: ProductLink.LinkType.SUBPRODUCT ).save(flush:true)
        new ProductLink( from: p3, to: p1, type: ProductLink.LinkType.RECOMMENDATION ).save(flush:true)
    }    
}
