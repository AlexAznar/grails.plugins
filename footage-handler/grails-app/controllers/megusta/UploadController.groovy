package megusta

import grails.plugins.springsecurity.Secured
import com.megusta.footagehandler.FileUtils
import com.megusta.footagehandler.ImageFileUtils
import com.megusta.footagehandler.ImageSettings
import grails.converters.JSON
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

class UploadController {

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index = {
        [ allowedExtensions: getConvertedExtensions(),
          maxFileSize: ConfigurationHolder.config.grails.plugins.megusta.footage.handler.maxFileSize.default as int ]
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def uploadImage = {

        ImageSettings settings = ImageSettings.get(params.settingsId)

        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
            MultipartFile imgMFile = request.getFile('qqfile')
            if(!imgMFile.isEmpty()) {
                String tempFileName = new Date().time + '.' + FileUtils.getFileExtension(imgMFile.getOriginalFilename())
                File imgFile = new File(ConfigurationHolder.config.grails.plugins.megusta.footage.handler.tempDir + File.separator + tempFileName)
                imgMFile.transferTo(imgFile)
                int imgWidth = ImageFileUtils.getPictureWidth(imgFile)
                int imgHeight = ImageFileUtils.getPictureHeight(imgFile)
                boolean isValidHeight = imgHeight > settings.minHeight
                boolean isValidWidth = imgWidth > settings.minWidth
                if(isValidHeight && isValidWidth) {
                    render(text:[success: true, fileName:tempFileName, imgOriginalWidth:imgWidth, imgOriginalHeight:imgHeight] as JSON, contentType:'text/plain')
                    return;
                } else {
                    String failureMessage = g.message(code: "error.upload.image.fail.dimensions", args: [settings.minWidth, settings.minHeight])
                    imgFile.deleteOnExit()
                    render(text:[success: false, message: failureMessage] as JSON, contentType:'text/plain')
                    return;
                }
            } else {
                render([sucess: false, error: 'File was empty'])
            }
        } else {
            String tempFileName = new Date().time + '.' + FileUtils.getFileExtension(params.qqfile as String)
            File imgFile = new File(ConfigurationHolder.config.grails.plugins.megusta.footage.handler.tempDir + File.separator + tempFileName)
            imgFile << request.inputStream
            int imgWidth = ImageFileUtils.getPictureWidth(imgFile)
            int imgHeight = ImageFileUtils.getPictureHeight(imgFile)
            boolean isValidHeight = imgHeight > settings.minHeight
            boolean isValidWidth = imgWidth > settings.minWidth
            if(isValidHeight && isValidWidth) {
                render(text:[success: true, fileName:tempFileName, imgOriginalWidth:imgWidth, imgOriginalHeight:imgHeight] as JSON, contentType:'text/plain')
            } else {
                String failureMessage = g.message(code: "error.upload.image.fail.dimensions", args: [settings.minWidth, settings.minHeight])
                imgFile.deleteOnExit()
                render(text:[success: false, message: failureMessage] as JSON, contentType:'text/plain')
            }
        }

    }


    /**
     * Returns allowed extensions converted to the view, that can be read by ajax uploader tag.
     *
     * @return allowed extensions
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY']) 
    static String getConvertedExtensions() {
        def extensions = ConfigurationHolder.config.grails.plugins.megusta.footage.handler.extensions.default
        StringBuffer sb = new StringBuffer()
        int counter = 0
        extensions.each {
            counter++
            sb.append("'$it'")
            if (counter != extensions.size()) {
                sb.append(",")
            }
        }

        sb.toString()
    }

}