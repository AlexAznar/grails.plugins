package com.scharfmedia.grails.plugins.pages

class PageFilters {

    def filters = {
        allPageViews(controller:'page', action:'view') {
            before = {                
            }
            after = { Map model ->
                def pages = com.scharfmedia.grails.plugins.pages.Page.list()
                model.pages = pages
            }
            afterView = { Exception e ->

            }
        }
    }
}
