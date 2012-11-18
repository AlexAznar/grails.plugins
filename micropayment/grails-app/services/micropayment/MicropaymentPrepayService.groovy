package micropayment

/**
 * Wrapper Servivce for the Micropayment Prepay API
 *
 * TODO: not yet all methods implemented
 * @see https://webservices.micropayment.de/public/prepay/v1.0/nvp
 **/
class MicropaymentPrepayService {

    /**
     * delete all customer and transaction data in test environment
     *
     * @see https://webservices.micropayment.de/public/prepay/v1.0/nvp/#resetTest_method
     **/
    def resetTest() {
		parseResponse(  buildUrl( "resetTest" ).text )		
    }

    /**
     * create a new customer
     *
     * @see https://webservices.micropayment.de/public/prepay/v1.0/nvp/#customerCreate_method
     **/
    def customerCreate( def customerId, def params ) {
		parseResponse( buildUrl( "customerCreate", ["customerId":customerId,freeParams:params] ).text )		
    }

    /**
     * get a list with all extra params for a specific customer
     *
     * @see https://webservices.micropayment.de/public/prepay/v1.0/nvp/#customerGet_method
     **/
    def customerGet( def customerId ) {
		parseResponse( buildUrl( "customerGet", ["customerId":customerId] ).text )		
    }

    /**
     * get a list of all customers
     *
     * @see https://webservices.micropayment.de/public/prepay/v1.0/nvp/#customerList_method
     **/
    def customerList() {
		parseResponse( buildUrl( "customerList" ).text )		
    }

    /**
     * create a new transaction for a specific customer and project
     *
     * @see https://webservices.micropayment.de/public/prepay/v1.0/nvp/#sessionCreate_method
     **/
    def sessionCreate( def customerId, def project, def amount, def title, def payText, def expireDays = 30, def params = [:] ) {
		def newParams 		 = params
		newParams.customerId = customerId
		newParams.project	 = project
		newParams.amount	 = amount
		newParams.title		 = title
		newParams.payText	 = payText
		newParams.expireDays = expireDays

		parseResponse( buildUrl( "sessionCreate", newParams ).text )		
    }

    /**
     * get a list of all sessions
     *
     * @see https://webservices.micropayment.de/public/prepay/v1.0/nvp/#sessionList_method
     **/
    def sessionList( def customerId ) {
		parseResponse( buildUrl("sessionList",[customerId:customerId]).text )
    }

	/**
	 * pay-in money for a specific session
	 *
	 * @see https://webservices.micropayment.de/public/prepay/v1.0/nvp/#sessionPayinTest_method
	 **/
	def sessionPayinTest( def sessionId, def amount ) {
		parseResponse( buildUrl("sessionPayinTest",[sessionId:sessionId, amount:amount]).text )
	}

	/**
	 * get details of a specific session
	 *
	 * @see https://webservices.micropayment.de/public/prepay/v1.0/nvp/#sessionGet_method
	 **/
	def sessionGet( def sessionId ) {
		parseResponse( buildUrl("sessionGet",[sessionId:sessionId]).text )
	}


	// ======================
    // IOC injected by grails
	// ======================
    def grailsApplication

	// ==============
	// HELPER METHODS
	// ==============
    /**
     * helper method for building access urls to the micropayment api
     **/
    private def buildUrl( def action, def params = [:], asString = false ) {
		return MicropaymentUtil.buildUrl( grailsApplication.config.grails.plugins.micropayment, action, params, asString )
    }

    /**
     * helper for parsing api response
     **/
    private def parseResponse( def text ) {
		return MicropaymentUtil.parseResponse( text )
    }

    /**
     * helper for building payment window link
     **/
    def createWindowLink( def params = [:], def sealed = true ) {
		return MicropaymentUtil.createWindowLink( grailsApplication.config.grails.plugins.micropayment, 'prepay', params, sealed )
    }
}
