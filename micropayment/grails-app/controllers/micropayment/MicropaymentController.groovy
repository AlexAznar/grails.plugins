package micropayment

class MicropaymentController {

	// callback services which can be overriden (ioc) by app
	def micropaymentCallbackService


	def index() {		
		// if service has this "function" method
		if( params.function && micropaymentCallbackService.metaClass.methods.find { it.name == params.function } )
		{
			def props = [:]
			// go through every key, value pair and transform values
			params.each { k, v -> props[k] = prepareValue(k,v,params) }
			// dynamically execute 'function'
			def response = micropaymentCallbackService."${params.function}"( props, params.type )

			response.status = 200	
			render "status=ok\nurl=${response.url}\ntarget=_self\nforward=1"
		}
		// we do not support this "function"
		else
		{
			response.status = 404			
			render "status=error"
		}	
	}

	// convert values to groovy data types
	private def prepareValue( def name, def value, def params ) {
		switch( name ) {
			case 'amount':
				// convert cent in eur values
				value ? new BigDecimal("${params.double(name) / 100}") : null
			break;

			case 'openamount':
				// convert cent in eur values
				value ? new BigDecimal("${params.double(name) / 100}") : null
			break;
			
			case 'paidamount':
				// convert cent in eur values
				value ? new BigDecimal("${params.double(name) / 100}") : null
			break;
			
			case 'expire':
				value ? params.date(name,'yyyy-MM-dd') : null
			break;

			case 'testmode':
				value ? params.int('testmode') : null
			break;
			
			default:
				value ? value : null
			break;
		}
	}

}
