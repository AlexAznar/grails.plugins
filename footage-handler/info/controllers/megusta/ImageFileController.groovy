package megusta

import com.megusta.footagehandler.ImageFile
import com.megusta.footagehandler.FileUtils
import com.megusta.footagehandler.ImageFileUtils
import com.megusta.footagehandler.ImageFileProfile
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ImageFileController {

    def list = {
        [images: ImageFile.findAll()]
    }

    def edit = {
        ImageFile imageFile = findById()
        [id: params.id]
    }

    def showImage = {
        ImageFile imageFile = ImageFile.findById(params.id)
        if (imageFile) {
            response.outputStream << imageFile.getFootageStream()
        }
    }

    def cropAction = {
        if (params.x && params.y && params.x2 && params.y2) {
            ImageFile imageFile = findById()
            File file = FileUtils.getTempFile(imageFile.extension)
            imageFile.getFootage(file)
            if (ImageFileUtils.cropUsersImage(file, params.x as int, params.y as int, params.x2 as int, params.y2 as int)) {
                imageFile.putFootage(file.newInputStream(), file.getName())
                file.delete()
                render view: 'edit', model: [editMessage: 'Image was cropped successfully', id: params.id]
            } else {
                file.delete()
                render view: 'edit', model: [editMessage: 'Image wasn\'t cropped. Try to clear browser cache', id: params.id]
            }
        } else {
            render view: 'edit', model: [editMessage: 'Please select the area to crop', id: params.id]
        }
    }

    def remove = {
        ImageFile imageFile = findById()
        imageFile.remove()
        imageFile.delete()
        redirect action: list
    }

    def adjust = {
        ImageFile imageFile = findById()
        def conf = ConfigurationHolder.getFlatConfig()
        ImageFileProfile profile = new ImageFileProfile(
                name: conf.get("grails.plugin.footagehandler.default.profile.name"),
                targetHeight: params.height ?: conf.get("grails.plugin.footagehandler.default.profile.targetHeight") as int,
                targetWidth: params.width ?: conf.get("grails.plugin.footagehandler.default.profile.targetWidth") as int,
                maxHorizontalCropping: conf.get("grails.plugin.footagehandler.default.profile.maxHorizontalCropping"),
                maxHorizontalScaling: conf.get("grails.plugin.footagehandler.default.profile.maxHorizontalScaling"),
                maxVerticalCropping: conf.get("grails.plugin.footagehandler.default.profile.maxVerticalCropping"),
                maxVerticalScaling: conf.get("grails.plugin.footagehandler.default.profile.maxVerticalScaling"))
        profile.save(flush: true)
        imageFile.imageFileProfile = profile
        imageFile.adjust()
        String editMessage
        if (imageFile.perfectTargetMatch) {
            editMessage = 'Image was adjusted successfully'
        } else {
            editMessage = 'Image can\'t be adjusted to this profile'
        }
        render view: 'edit', model: [editMessage: editMessage, id: params.id]
    }

    /**
     * Gets imageFile by id.
     *
     * @return image file
     */
    protected ImageFile findById() {
        ImageFile imageFile = ImageFile.findById(params.id)
        if (!imageFile) {
            redirect action: list
        }

        imageFile
    }
}
