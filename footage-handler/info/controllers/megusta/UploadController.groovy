package megusta

import grails.converters.JSON
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import com.megusta.footagehandler.ImageFile
import com.megusta.footagehandler.ImageCollection

class UploadController {

    def index = {
        [command: new CreatePictureCommand()]
    }

    def upload = {CreatePictureCommand cmd ->
        ImageFile imageFile = new ImageFile()
        //just a little hack to show you how interaction with collection works
        String collectionName = 'sampleCollection'
        if (ImageCollection.count() == 0) {
            new ImageCollection(name: collectionName).save(flush: true)
        }
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) mpr.getFile("qqfile")
            if (!imageFile.customValidate(file, cmd, "errorField")) {
                render view: 'index', model: [command: cmd]
                return
            }
            imageFile.putFootage(file)
            ImageCollection.findByName(collectionName).addToImages(imageFile).save(flush: true)
            render([success: true] as JSON)
        } else {
            imageFile.putFootage(request.inputStream, params.qqfile)
            ImageCollection.findByName(collectionName).addToImages(imageFile).save(flush: true)
            render([success: true] as JSON)
        }
    }
}

class CreatePictureCommand {
    String errorField

    static constraints = {
    }
}