package com.scharfmedia.grails.plugins.pages

import org.springframework.web.servlet.support.RequestContextUtils as RCU 


class PageService {

    /**
     * prepare page for rendering
     * e.g. set null values to default values
     **/
    def preparePage( Page page ) {
        // set default layout
        if( !page.layout ) {
            page.layout = "main"
        }
        
        return page
    }

    /**
     * prepare page content for rendering
     * e.g. set null values to default values
     **/
    def preparePageContent( PageContent content ) {
        // set default locale
        if( !content.locale ) {
            content.locale = java.util.Locale.US
        }

        return content
    }
}
