package com.scharfmedia.grails.plugins.pages

class PageContent {

    String uid = java.util.UUID.randomUUID().toString()
    String content
    String section
    String controller
    String action
    String template
    String file
    String locale
    Integer index   = 10

    static constraints = {
        uid        nullable: false, unique: true
        content    nullable: true, blank:true
        section    nullable: true, blank:false
        locale     nullable: true, blank:false
        index      nullable: false
        controller nullable: true, blank:false
        action     nullable: true, blank:false
        template   nullable: true, blank:false
        file       nullable: true, blank:false
    }
}
