package com.megusta.footagehandler

class ImageSettings {

    String label

    //editing properties
    Double acceptableQuality = 0.0
    Double maxFileSize = 524288 * 2
    String allowedExtensions = "'jpg','jpeg','png','img'"
    String aspectRatio

    //the desired size of the image
    int targetWidth
    int targetHeight

    static transients = ['minWidth', 'minHeight']

    static hasMany = [derivedImages:ImageSettings]

    static constraints = {
        aspectRatio(nullable:true)
    }

    int getMinWidth() {
        targetWidth * acceptableQuality
    }

    int getMinHeight() {
        targetHeight * acceptableQuality
    }

}
