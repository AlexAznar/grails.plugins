package com

import com.megusta.footagehandler.AwsFile

class UploadController {

    def uploadPicture = {
        def useInstance = new CreatePictureCommand()
        return [useInstance: useInstance]
    }

    def savePicture = { CreatePictureCommand cmd ->

        def baseImage = new AwsFile()
        def hasError = baseImage.customValidate(params.picture, cmd, "name")

        if (hasError) {
            render(view: "uploadPicture", model: [useInstance: cmd])
        }
        else {

            baseImage.addionalSubdir = "videos/"
            baseImage.transferFile(params.picture)
            baseImage.save()

            //render view: '/general/message', model: [message: "saved"]
            render baseImage.absoluteLink()
        }
    }

}

class CreatePictureCommand {
    String name

    static constraints = {

    }
}