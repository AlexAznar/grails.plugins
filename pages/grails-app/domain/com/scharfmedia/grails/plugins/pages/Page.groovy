package com.scharfmedia.grails.plugins.pages

import org.springframework.web.servlet.support.RequestContextUtils as RCU 


class Page {
    
    String uid     = java.util.UUID.randomUUID().toString()

    String  name
    String  title
    String  layout  = 'main'
    String  theme

    static hasMany = [contents:PageContent]


    // params can have
    // - index  for order
    // - locale  for locale
    // - controller  for controller
    // - action  for controller action
    public def addContent( params = [:], Closure c = null ) {
        def content = new PageContent()
        content.controller = params.controller
        content.action     = params.action
        content.template   = params.template
        content.section    = params.section
        content.content    = params.content ?: c?c.call():null
        content.locale     = params.locale ? "${params.locale.getLanguage().toLowerCase()}_${params.locale.getCountry().toUpperCase()}" : null

        addToContents( content )
    }

    /**
     * get content for section or body
     **/
    public def getContent( String section = null ) {
        // filter contents
        def contentList = contents?.findAll { c ->
            // find by section
            section == null ? (c.section == null ? true : false) : (c.section == section ? true : false)
        }
    }
    
    static constraints = {
        uid     nullable: false, blank: false, unique: true
        name    nullable: false, blank: false, unique: true
        title   nullable: false, blank: false
        layout  nullable: true,  blank: false
        theme   nullable: true,  blank: false
    }
}
