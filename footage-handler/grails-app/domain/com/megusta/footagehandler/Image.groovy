package com.megusta.footagehandler

class Image {

    ImageSettings settings
    Asset asset

    static hasMany = [images:Image]

    static constraints = {
        asset nullable:false
    }

    def getImageByTag(String tag) {
        def value = null
        
        if (tag == this.settings.label) {
            value = this
        } else {
            this.images.each {
                if (tag == it.settings.label) {
                    value = it
                }
            }
        }
        return value
    }
}
