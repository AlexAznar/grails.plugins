modules = {
    footage_handler_uploader {

        dependsOn(['jquery-ui'])

        resource url: [dir:'css/imgAreaSelect', file:'imgareaselect-animated.css', plugin:'footage-handler']
//        resource url: [dir:'css/ui-lightness', file:'jquery-ui-1.8.16.custom.css', plugin:'footage-handler']
        resource url: [dir:'css/img-edit', file:'img-edit.css', plugin:'footage-handler']

        resource url: [dir:'js', file:'jquery.fileupload.js', plugin:'footage-handler']
        resource url: [dir:'js', file:'jquery.iframe-transport.js', plugin:'footage-handler']
        resource url: [dir:'js', file:'jquery.imgareaselect.pack.js', plugin:'footage-handler']
    }

    footage_handler_image_handler {
        dependsOn(['jquery'])

        resource url: [dir:'js', file:'jquery.Jcrop.min.js', plugin:'footage-handler']
    }
}
