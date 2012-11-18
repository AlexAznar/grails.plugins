package com.megusta.footagehandler

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class FileUploadService {

    static transactional = true

    /**
     * Returns allowed extensions converted to the view, that can be read by ajax uploader tag.
     *
     * @return allowed extensions
     */
    String getConvertedExtensions() {
        def extensions = CH.getFlatConfig().get('grails.plugins.megusta.footage.handler.extensions.default');
        StringBuffer sb = new StringBuffer()
        int counter = 0
        extensions.each {
            counter++
            sb.append("'$it'")
            if (counter != extensions.size()) {
                sb.append(",")
            }
        }
        println sb.toString()
        sb.toString()
    }

    /**
     * Returns an existing file inside the temp folder.
     * @param fileName
     * @return
     */
    File getTempFile(String fileName) {
        String tempDirPath = CH.config.grails.plugins.megusta.footage.handler.tempDir
        new File(tempDirPath + File.separator + fileName)
    }

}
