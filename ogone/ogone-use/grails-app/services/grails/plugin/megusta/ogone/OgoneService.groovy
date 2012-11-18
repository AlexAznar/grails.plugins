package grails.plugin.megusta.ogone

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class OgoneService {

    def processIncomingData(def params) {
        def hashParams = createIncomingMap(params)
        def hashParamsUpper = makeMapUpperCase(hashParams)

        def ogone = OgoneTransaction.findByOrderId(hashParamsUpper.ORDERID)
        if (ogone == null) {
            throw new Exception("orderId.not.found")
        } else {
            saveIncomingData(ogone, hashParamsUpper)
            ogone.updateStatus()
        }
    }

    def saveIncomingData(def ogone, def hashParamsUpper) {
        def exclude = ["ORDERID"]
        
        hashParamsUpper.each {
            if (!exclude.contains(it.key) && it.value != null && it.value != ""){
                ogone["back" + it.key] = it.value
            }
        }

        ogone.save()
    }

    def checkIncomingData(def params) {
        def hashParams = createIncomingMap(params)

        if ( makeOgoneSHAOut(hashParams).toUpperCase() == (params.SHASIGN).toUpperCase() ){
            return true
        } else {
            return false
        }
    }

    def makeOgoneSHAIn(def parameters) {
        def secretKey = ConfigurationHolder.config.grails.plugin.megusta.ogone.signIn
        return makeOgoneSHA(parameters, secretKey)
    }

    def makeOgoneSHAOut(def parameters) {
        def secretKey = ConfigurationHolder.config.grails.plugin.megusta.ogone.signOut
        return makeOgoneSHA(parameters, secretKey)
    }

    def makeOgoneSHA (def parameters, def secretKey) {
        String beforeSHA = ""

        parameters = makeMapUpperCase(parameters)
        parameters = parameters.sort()

        parameters.each {
            if (it.value != null && it.value != "") {
                beforeSHA += it.key
                beforeSHA += "="
                beforeSHA += it.value
                beforeSHA += secretKey
            }
        }

        return beforeSHA.encodeAsSHA1()
    }
    
    def makeMapUpperCase(def orgMap) {
        def resultMap = [:]
        
        orgMap.each {
            resultMap.put(it.key.toUpperCase(), it.value)
        }

        return resultMap
    }

    def createIncomingMap(def params) {
        def hashParams = [:]
        def exclude = ["action", "controller", "SHASIGN"]

        params.each {
            if (!exclude.contains(it.key)){
                hashParams.put(it.key, it.value)
            }
        }

        return hashParams
    }

    def initDirectOgone(){

        def ogone = new OgoneTransaction()

        ogone.homeUrl = null
        ogone.acceptUrl = null
        ogone.exceptionUrl = null
        ogone.declineUrl = null
        ogone.backurl = null
        ogone.cancelUrl = null
        ogone.userId = ConfigurationHolder.config.grails.plugin.megusta.ogone.direct.userId
        ogone.pswd = ConfigurationHolder.config.grails.plugin.megusta.ogone.direct.password
        ogone.operation = "SAL"
        ogone.save()

        return ogone
    }

    def makeParameterString(def notNullProperties) {
        def response = ""
        def i = 1
        notNullProperties.each {
            response += URLEncoder.encode(it.key.toUpperCase())
            response += "="
            response += URLEncoder.encode(it.value)
            if (i < notNullProperties.size()){
                response += "&"
            }
            i++
        }

        return response
    }

    def getDirectResponse(ogone) {
        def notNullProperties = ogone.notNullPropertiesMap()
        def ogoneHash = makeOgoneSHAIn(notNullProperties)
        notNullProperties.put("SHASign", ogoneHash)

        def link = ConfigurationHolder.config.grails.plugin.megusta.ogone.direct.link

        def myresponse = null

        try {
            def url = new URL(link)

            def conn = url.openConnection()
            conn.setRequestMethod("POST")
            conn.doOutput = true

            Writer wr = new OutputStreamWriter(conn.outputStream)
            wr.write(makeParameterString(notNullProperties))
            wr.flush()
            wr.close()
            conn.connect()

            myresponse = conn?.content?.text
        } catch (Exception e) {
            println e
        }

        return myresponse
    }
}
