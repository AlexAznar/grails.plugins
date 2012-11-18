package com.megusta.footagehandler

class ImageFileProfile {


    String name //Profile name.
    int targetWidth //Target image width.
    int targetHeight //Target image height.
    int maxHorizontalScaling = 0 //Max horizontal scaling.
    int maxVerticalScaling = 0 //Max vertical scaling.
    int maxHorizontalCropping = 0 //Max horizontal cropping.
    int maxVerticalCropping = 0 //Max vertical cropping.

    static constraints = {
        name blank: false
    }

}
