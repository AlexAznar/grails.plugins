package grails.plugins.megusta.utilities

class GenHelperTagLib {

    static namespace = "genHelper"
    def springSecurityService
    def simpleCheckBox = {attrs, body ->
        out << g.renderErrors(bean: attrs.bean, field: extractFieldName(attrs.field)) {}
        out << "<label for=${attrs.field}>${extractFieldName(attrs.field)}</label>"
        out << g.checkBox(name: attrs.field, value: attrs.bean."${attrs.field}") {}
        out << "</br>"
    }

    def simpleTextField = {attrs, body ->
        out << g.renderErrors(bean: attrs.bean, field: extractFieldName(attrs.field)) {}
        out << "<label for=${attrs.field}>${extractFieldName(attrs.field)}</label>"
        out << g.textField(name: attrs.field, value: getValue(attrs)) {}
        out << "</br>"
    }

    def simpleTextArea = {attrs, body ->
        out << g.renderErrors(bean: attrs.bean, field: extractFieldName(attrs.field)) {}
        out << "<label for=${attrs.field}>${extractFieldName(attrs.field)}</label>"
        out << g.textArea(name: attrs.field, value: getValue(attrs)) {}
        out << "</br>"
    }

    def simpleSelectBox = {attrs, body ->
        out << g.renderErrors(bean: attrs.bean, field: extractFieldName(attrs.field)) {}
        out << "<label for=${attrs.field}>${extractFieldName(attrs.field)}</label>"
        out << g.select(name: "${attrs.field}.id", value: attrs?.bean?.gender?.id, from: attrs.from, optionKey: 'id', optionValue: "name") {}
        out << "</br>"
    }

    def getValue(def attrs) {
        if (getFieldSize(attrs.field) == 1) return attrs.bean."${attrs.field}"
        if (getFieldSize(attrs.field) > 1) {
            def output = attrs.bean."${extractFieldName(attrs.field)}"."${extractSecondFieldName(attrs.field) }"
            return output
        }
    }

    def creatPagination = {attrs, body ->
        int offset = Integer.parseInt(attrs['offset'].toString());
        int pCount = Integer.parseInt(attrs['pcount'].toString());
        int totalPerformar = Integer.parseInt(attrs['totalPerformar'].toString());
        int max = Integer.parseInt(attrs['max'].toString());
        int j = 0;
        for (int i = 0; i < totalPerformar; i = i + max) {
            out << "<input type='button'  onclick='searchingSorting(${i})' value='${++j}' name='Next'/>"
        }
        out << "<select id='max'>"
        for (int i = 5; i <= 40; i = i + 5) {
            out << "<option value='${i}'>" + i + "</option>"
        }
        out << "</select>"
    }

    def simpleUserFieldsOnMain = {attrs, body ->
        def user = springSecurityService.currentUser;
        if (user.approved == false && user.accountTyp == "performerUser") {
            StringBuffer wait = new StringBuffer();
            StringBuffer message = new StringBuffer();
            wait.append("waiting for activation ....")
            if (!user.s3_identification) {
                message = message.append("&nbsp; Identifiaction image &nbsp;");
            }
            if (!user.s3_imageWithIdent) {
                message = message.append("&nbsp;  Image with ideantification &nbsp;");
            }
            if (!user.s3_image01) {
                message = message.append("&nbsp;  Image_01 &nbsp;");
            }
            if (message) {
                StringBuffer missing = new StringBuffer();
                missing.append("Missing Fields : ")
                missing.append(message);
                out << missing;
            } else {
                out << wait;
            }
        }
    }

    def simpleUnEditableTextField = {attrs, body ->
        out << g.renderErrors(bean: attrs.bean, field: extractFieldName(attrs.field)) {}
        out << "<label for=${attrs.field}>${extractFieldName(attrs.field)}</label>"
        out << "&nbsp;&nbsp;&nbsp;" + getValue(attrs)
        out << "</br>"
    }

    def extractSecondFieldName(def input) {
        def output = input.tokenize(".")
        return output[1]
    }

    def extractFieldName(def input) {
        def output = input.tokenize(".")
        return output[0]
    }

    def getFieldSize(def input) {
        def output = input.tokenize(".")
        return output.size()
    }

}
