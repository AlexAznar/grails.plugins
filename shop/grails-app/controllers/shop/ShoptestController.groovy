package shop

class ShoptestController {

    def index() {
        [catalog: Catalog.list()[0] ]
    }
}
