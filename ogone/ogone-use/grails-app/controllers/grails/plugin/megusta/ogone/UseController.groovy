package grails.plugin.megusta.ogone

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class UseController {

    def ogoneService

    def home () {
        render "clicked on home"
    }
    
    def selectPayment (){
        def ogone = new OgoneTransaction()

        ogone.amountNumber = 15
        ogone.language = "en_US"
        ogone.email = "christian.rommel@megustaenterprise.de"
        ogone.cn = "meier"
        ogone.com = "video"
        ogone.tp = "https://secure.ogone.com/ncol/template_standard.htm"
        ogone.save()

        def payments = ["PAYPAL", "CreditCard",]
        def ogoneMap = [:]

        payments.each {
            def details = [:]
            
            ogone.pm = it

            def notNullProperties = ogone.notNullPropertiesMap()
            def ogoneHash = ogoneService.makeOgoneSHAIn(notNullProperties)
            
            details.put("hash", ogoneHash)
            details.put("ogone", notNullProperties)
            
            ogoneMap.put(it, details)
        }
                    
        return [ogoneMap: ogoneMap, url: ogone.submitUrl]
    }

    def makePayment() {
        def ogone = new OgoneTransaction()

        ogone.amountNumber = 15
        ogone.language = "en_US"
        ogone.email = "christian.rommel@megustaenterprise.de"
        ogone.pm = "CreditCard"
        ogone.cn = "meier"
        ogone.com = "video"
        ogone.alias = "test34"
        ogone.aliasUsage = "abo"
        ogone.aliasOperation = "BYMERCHANT"

        ogone.save()

        def notNullProperties = ogone.notNullPropertiesMap()
        def ogoneHash = ogoneService.makeOgoneSHAIn(notNullProperties)

        return [ogone: notNullProperties, ogoneHash: ogoneHash, url: ogone.submitUrl]
    }

    def makeDirectLink() {
        def ogone = ogoneService.initDirectOgone()

        ogone.amountNumber = 15
        ogone.cardNo = "4111111111111111"
        ogone.alias = "test35"
        ogone.ed = "1216"
        ogone.cvc = "212"
        ogone.save()

        def response = ogoneService.getDirectResponse(ogone)
        render response.toString()
    }

    def useAlias() {
        def ogone = ogoneService.initDirectOgone()

        ogone.amountNumber = 88
        ogone.alias = "test35"
        ogone.eci = 9
        ogone.save()

        def response = ogoneService.getDirectResponse(ogone)
        render response.toString()
    }   

    def uiResponse () {
        def correct = ogoneService.checkIncomingData(params)

        if (correct){
            ogoneService.processIncomingData(params)
            render "correct"
        } else {
            render "no.hacking.please"
        }
    }

    def serverCall () {
        def correct = ogoneService.checkIncomingData(params)

        if (correct){
            ogoneService.processIncomingData(params)
            println "correct"
            render "correct"
        } else {
            println "no.hacking.please"
            render "no.hacking.please"
        }
    }
}
