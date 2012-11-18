package megusta

import com.megusta.footagehandler.ImageFileUtils
import grails.converters.JSON
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.plugins.springsecurity.Secured

class ImageFileController {

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def showTempImage = {
        File imgFile = new File(ConfigurationHolder.config.grails.plugins.megusta.footage.handler.tempDir + File.separator + params.fileName)
        response.outputStream << imgFile.bytes
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save = {
        int imgWidth = params.imgWidth as int
        int imgHeight = params.imgHeight as int
        String fileName = params.fileName
        String tempDirPath = ConfigurationHolder.config.grails.plugins.megusta.footage.handler.tempDir
        def file = new File(tempDirPath + File.separator + fileName)
        ImageFileUtils.stretch(file, imgWidth, imgHeight)
        ImageFileUtils.cropUsersImage(file, params.cropX as int, params.cropY as int, params.cropX2 as int, params.cropY2 as int)
        def result = [success:true, fileName:fileName]
        render result as JSON
    }


}
