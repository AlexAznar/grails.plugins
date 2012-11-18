package grails.plugin.megusta.ogone

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class OgoneTransaction {

    Date lastUpdated
    Date dateCreated

    String exclude = "exclude,dateCreated,lastUpdated,status,submitUrl,amountNumber"

    BigDecimal amountNumber
    String getAmount(){
        if (amountNumber != null) {
            return (amountNumber * 100).intValue().toString()
        } else {
            return null
        }
    }

    Status status = Status.CREATED

    String orderId = (UUID.randomUUID().toString() + new Date() ).substring(0, 29)
    String pspid = ConfigurationHolder.config.grails.plugin.megusta.ogone.pspid
    String currency = ConfigurationHolder.config.grails.plugin.megusta.ogone.default.currency
    String acceptUrl = ConfigurationHolder.config.grails.plugin.megusta.ogone.uiResponse
    String declineUrl = ConfigurationHolder.config.grails.plugin.megusta.ogone.uiResponse
    String exceptionUrl = ConfigurationHolder.config.grails.plugin.megusta.ogone.uiResponse
    String cancelUrl = ConfigurationHolder.config.grails.plugin.megusta.ogone.uiResponse
    String backurl = ConfigurationHolder.config.grails.plugin.megusta.ogone.backurl
    String homeUrl = ConfigurationHolder.config.grails.plugin.megusta.ogone.homeurl
    String submitUrl = ConfigurationHolder.config.grails.plugin.megusta.ogone.submitUrl

    String userId
    String pswd
    
    String language
    String email
    String ownerAddress
    String ownerZip
    String ownerTown
    String ownerCity
    String ownerCty //country of customer
    String ownerTeno //telefon of customer
    String cn //name of customer
    String com //order description
    String pm
    String brand
    
    String alias
    String aliasUsage
    String aliasOperation
    String txToken
    String eci
    
    String logo
    String title
    String tp
    String device
    
    String cardNo
    String cvc
    String ed
    String operation

    String backACCEPTANCE
    String backAMOUNT
    String backBIN
    String backBRAND
    String backCARDNO
    String backCN
    String backCOMPLUS
    String backCURRENCY
    String backECI
    String backED
    String backIP
    String backNCERROR
    String backPAYID
    String backPM
    String backSTATUS
    String backSUBBRAND
    String backTRXDATE
    
    String backAAVADDRESS
    String backAAVCHECK
    String backAAVZIP
    String backCCCTY
    String backCVCCHECK
    String backIPCTY
    String backVC
    String backALIAS

    static constraints = {
        pspid(nullable: true)
        userId(nullable: true)
        pswd(nullable: true)
        orderId(nullable: true)
        amount(nullable: true)
        amountNumber(nullable: true)
        currency(nullable: true)
        language(nullable: true)
        cn(nullable: true)
        com(nullable: true)
        email(nullable: true)
        ownerAddress(nullable: true)
        ownerZip(nullable: true)
        ownerTown(nullable: true)
        ownerCity(nullable: true)
        ownerCty(nullable: true)
        ownerTeno(nullable: true)
        
        pm(nullable: true)
        brand(nullable: true)
        logo(nullable: true)
        title(nullable: true)
        tp(nullable: true)
        device(nullable: true)
        alias(nullable: true)
        aliasUsage(nullable: true)
        aliasOperation(nullable: true)
        txToken(nullable: true)
        eci (nullable: true)

        homeUrl(nullable: true)
        backurl(nullable: true)
        cancelUrl(nullable: true)
        acceptUrl(nullable: true)
        declineUrl(nullable: true)
        exceptionUrl(nullable: true)

        cardNo(nullable: true)
        cvc(nullable: true)
        ed(nullable: true)
        operation(nullable: true)

        backACCEPTANCE nullable: true
        backAMOUNT nullable: true
        backBIN nullable: true
        backBRAND nullable: true
        backCARDNO nullable: true
        backCN nullable: true
        backCOMPLUS nullable: true
        backCURRENCY nullable: true
        backECI nullable: true
        backED nullable: true
        backIP nullable: true
        backNCERROR nullable: true
        backPAYID nullable: true
        backPM nullable: true
        backSTATUS nullable: true
        backSUBBRAND nullable: true
        backTRXDATE nullable: true

        backAAVADDRESS nullable: true
        backAAVCHECK nullable: true
        backAAVZIP nullable: true
        backCCCTY nullable: true
        backCVCCHECK nullable: true
        backIPCTY nullable: true
        backVC nullable: true
        backALIAS(nullable: true)
    }

    static transients = ['amount']

    def notNullPropertiesMap() {
        def properties = [:]

        def excludeAr = this.exclude.split(",")

        this.properties.each {
            if (it.value != null && !excludeAr.contains(it.key)) {
                properties.put(it.key, it.value)
            }
        }

        return properties
    }

    def updateStatus() {
        def decline = ["2"]
        def cancel = ["1"]
        def success = ["9"]

        if (success.contains(backSTATUS)) {
            status = Status.SUCCESS
        } else if (cancel.contains(backSTATUS)) {
            status = Status.CANCEL
        } else if (decline.contains(backSTATUS)) {
            status = Status.DECLINE
        } else {
            status = Status.EXCEPTION
        }

        this.save()
    }

}

public enum Status {
    CREATED,
    LEFT_TO_OGONE,
    DECLINE,
    EXCEPTION,
    CANCEL,
    SUCCESS
}