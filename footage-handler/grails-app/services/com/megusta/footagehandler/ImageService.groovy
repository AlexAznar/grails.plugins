package com.megusta.footagehandler

class ImageService {

    static transactional = true

    def storageService
    def fileUploadService

    File proportionalSqueeze(File imageFile, int defaultDisplayWidth, int defaultDisplayHeight, int width, int height ) {
        int originalHeight = ImageFileUtils.getPictureHeight(imageFile)
        int originalWidth = ImageFileUtils.getPictureWidth(imageFile)
        width = (originalWidth * width) / defaultDisplayWidth
        height = (originalHeight * height) / defaultDisplayHeight
        ImageFileUtils.stretch(imageFile, width, height)
        imageFile
    }

    File proportionalCrop(File imageFile, int displayWidth, int displayHeight, int x, int y, int x2, int y2) {
        int originalHeight = ImageFileUtils.getPictureHeight(imageFile)
        int originalWidth = ImageFileUtils.getPictureWidth(imageFile)
        x = (originalWidth * x) / displayWidth
        x2 = (originalWidth * x2) / displayWidth
        y = (originalHeight * y) / displayHeight
        y2 = (originalHeight * y2) / displayHeight
        ImageFileUtils.cropUsersImage(imageFile, x, y, x2, y2)
        imageFile
    }

    Image createImage(File file, ImageSettings imageSettings) {
        ImageFileUtils.adjust(file, imageSettings.targetWidth, imageSettings.targetHeight)
        def asset = storageService.transfer(file) as S3Asset
        Image image = new Image(asset:asset, settings:imageSettings)
        int i = 1
        imageSettings.derivedImages.each { ImageSettings settings ->
            def derivedFile = new File(file.parentFile.canonicalPath + File.separator + i + "_" + FileUtils.getFileName(file))
            derivedFile << file.bytes
            image.addToImages(createImage(derivedFile, settings))
        }
        file.delete()
        return image.save()
    }

    Image adjustAndSave(File file, def editingProps) {
        ImageSettings imageSettings = ImageSettings.get(editingProps.settingsId)
        proportionalSqueeze(file, (editingProps.imgOriginalWidth as double).intValue() , (editingProps.imgOriginalHeight as double).intValue(), (editingProps.imgWidth as double).intValue(), (editingProps.imgHeight as double).intValue())
        proportionalCrop(file, (editingProps.imgWidth as double).intValue(), (editingProps.imgHeight as double).intValue(), (editingProps.cropX as double).intValue(), (editingProps.cropY as double).intValue(), (editingProps.cropX2 as double).intValue(), (editingProps.cropY2 as double).intValue())
        Image image = createImage(file, imageSettings)
        return image
    }

    Image getEditedImage(def editingProperties) {
        File file = fileUploadService.getTempFile(editingProperties.fileName)
        adjustAndSave(file, editingProperties)
    }
}
