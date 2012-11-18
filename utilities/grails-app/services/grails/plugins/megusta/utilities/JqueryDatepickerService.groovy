package grails.plugins.megusta.utilities

import java.text.SimpleDateFormat

class JqueryDatepickerService {

    static transactional = true

    def initDateDeString(def params, def field) {
            if (params."${field}" != null && params."${field}" != "") {
                params.remove(field)
                return "" + params."${field}_day" + "." + params."${field}_month" + "." + params."${field}_year"
            } else {
                params.remove(field)
                return null
            }
        }

        def processDateDePrebinding(def params, def field) {
            def result
            if (params."${field}" != null && params."${field}" != "") {
                result = false
                setDeDateDetails(params, field)
            } else {
                result = true
                setDummyDate(params, field)
            }
            return result
        }

        def setDeDateDetails(def params, def field) {
            def simpleDateFormat = new SimpleDateFormat("dd.MM.yy")
            def date = simpleDateFormat.parse(params."${field}")
            params."${field}_date" = date

            simpleDateFormat = new SimpleDateFormat("dd")
            params."${field}_day" = simpleDateFormat.format(date)

            simpleDateFormat = new SimpleDateFormat("MM")
            params."${field}_month" = simpleDateFormat.format(date)

            simpleDateFormat = new SimpleDateFormat("yyyy")
            params."${field}_year" = simpleDateFormat.format(date)
        }

        def setDummyDate(def params, def field) {
            params."${field}_date" = "1"
            params."${field}_month" = "1"
            params."${field}_year" = "2000"
        }

}
