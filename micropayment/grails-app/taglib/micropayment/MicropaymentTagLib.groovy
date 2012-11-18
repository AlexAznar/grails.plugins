package micropayment

class MicropaymentTagLib {

    static namespace = "micropayment"

	def micropaymentService
	def grailsApplication


	/**
	 * create payment window link
	 * usage:
	 *  <micropayment:link type="prepay" account="12345" project="mprj" theme="x1" gfx="x-clouds" bgcolor="ebebeb" amount="1000" title="article" paytext="product text" sealed="1" params="[]" />
	 * or
	 * ${micropayment.link(type:'prepay',amount:'12345' ... )}
	 **/
	def link = { attrs, body ->
		// get micropayment config
		def config = grailsApplication.config.grails.plugins.micropayment
		// get or initialize params
		def params = attrs.params ?: [:]
		// seal url
    	def sealed = attrs.sealed && (attrs.sealed == 0 || attrs.sealed == "0") ? false : true
		// type like debit, creditcard, call2pay, prepay, ebank2pay
		def type = attrs.type
		// get type configuration
		def typeConfig = config.api."${type}"
		// no type config is fatal
		if( !typeConfig ) {
		    throw new NullPointerException( "grails-micropayment-plugin missconfiguration in your app config! Payment Type for config 'grails.plugins.micropayment.api.${type}' must be configured." )
		}

		// get fix parameters
		// the micropayment project , default to config
		params.project = attrs.project ?: params.project ?: config.project
		// the micropayment project , default to config
        params.projectcampaign = attrs.projectcampaign ?: params.projectcampaign ?: null
		// micropayment account no of owner or reseller
		params.account = attrs.account ?: params.account ?: config.account
		// payment window layout like l2, l3, x1 (default)
		params.theme = attrs.theme ?: params.theme ?: 'x1'
		// payment window image like x-clouds, x-default, x-fish, x-relaxing
		params.gfx = attrs.gfx ?: params.gfx ?: 'x-clouds'
		// payment window background color
		params.bgcolor = attrs.bgcolor ?: params.bgcolor ?: 'EBEBEB'
		
		// important parameters
		// price to pay in euro cent
		params.amount = attrs.amount ?: params.amount
		// title of product
		params.title = attrs.title ?: params.title
		// advanced title used in payment descriptions
		params.paytext = attrs.paytext ?: params.paytext

		// render url
		out << MicropaymentUtil.createWindowLink( config, type, params, sealed )
		out.flush()
	}


}
