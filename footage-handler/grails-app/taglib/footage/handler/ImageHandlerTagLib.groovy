package footage.handler

class ImageHandlerTagLib {

    static namespace = 'img'

    def head = { attrs, body ->
        r.require(module: 'footage_handler_image_handler')

        r.script(disposition: "head", "jQuery.noConflict();")

        String cssPath = attrs.css ?: resource(plugin: 'footage-handler', dir:"css", file:'jquery.Jcrop.css')

        out << '<style type="text/css" media="screen">'
        out << "   @import url( ${cssPath} );"
        out << "</style>"
    }

    def edit = { attrs, body ->
        def id = attrs.id
        out << """
        <script type="text/javascript">

            var x, y, x2, y2;

            jQuery(function(\$) {
                \$('#${id}').Jcrop({
                  onChange: onCropChangeHandler
                });
            });

            function onCropChangeHandler(c) {
                x = c.x;
                y = c.y;
                x2 = c.x2;
                y2 = c.y2;
            }

        </script>
        """
    }


}
