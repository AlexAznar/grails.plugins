package com.megusta.footagehandler

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH


class TempAsset extends Asset {

    String fileName

    static transients = ['file']

    static constraints = {
        fileName(nullable:false, blank:false)
    }

    TempAsset() {}

    TempAsset(File file, String fileName) {
        this.fileName = fileName
        newFile(file)
    }

    File getFile() {
        String tempDirPath = CH.config.grails.plugins.megusta.footage.handler.tempDir
        new File(tempDirPath + File.separator + fileName)
    }

}
