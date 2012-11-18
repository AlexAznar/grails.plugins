package com.scharfmedia.grails.plugins.pages

class PageController {

    static layout = 'pages/pages'

    def pageService
    

    def index() { }

    def view() {
        def p = Page.findByName( params.id )
        if( !p )
            throw new NullPointerException( "page ${params.id} not found" )        
        
        def page     = pageService.preparePage(p)
        page.contents?.each { c ->
            c = pageService.preparePageContent(c)
        }

        [page:page]
    }

    def irgendwas() {
        render "irgendwas am ${new java.util.Date()}"
    }
}
