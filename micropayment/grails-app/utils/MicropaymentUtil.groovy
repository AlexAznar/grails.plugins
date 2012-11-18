package micropayment;

class MicropaymentUtil {

    /**
     * helper method for building access urls to the micropayment api
     **/
    def static buildUrl( def config, def action, def params = [:], asString = false ) {
		// retrieve parameter from config
		def url  = config.api.prepay.url
		def key  = config.api.accesskey
		def test = config.api.testmode ?: 1

		// just return base url if no action
		if( !action ) {
			return url
	    }
		// build complex api url with parameters and preset accesskey and testmode
		else {
			// build base url
			def safeAction = URLEncoder.encode( action, "UTF-8" )
			def newUrl = "${url}/?action=${safeAction}&accessKey=${key}&testMode=${test}"

			// append extra params
			params.each { k, v ->
				if( v instanceof java.util.Map ) {
					v.each { kk,vv ->
						def xkeykey = URLEncoder.encode( kk, "UTF-8" )
						def xkey    = URLEncoder.encode( k, "UTF-8" )
						def xvalue  = URLEncoder.encode( vv, "UTF-8" )

						newUrl += "&${xkey}[${xkeykey}]=${xvalue}"
					}
				}
				else {
					def xkey   = URLEncoder.encode( k, "UTF-8" )
					def xvalue = URLEncoder.encode( v, "UTF-8" )

					newUrl += "&${xkey}=${xvalue}"
				}
			}

			return asString ? newUrl : new URL(newUrl)
		}
    }

    /**
     * helper for parsing api response
     **/
    def static parseResponse( def text ) {
		def params = [:]

		BufferedReader reader = new BufferedReader( new StringReader(text) );
		reader.eachLine { line ->
			def kv = line.trim().split( "=" )

			def s1 = 0, s2 = 0
			if( (s1=kv[0].indexOf("[")) > 0 && (s2=kv[0].indexOf("]")) > 0 ) {
			    def main = kv[0].substring(0,s1)
			    def sub  = kv[0].substring(s1+1,s2)

			    if( !params[main] )
				params[main] = [:]

			    params[main][sub] = kv.length > 1 ? URLDecoder.decode( kv[1], "UTF-8" ) : ""
			}
			else {
			    params[kv[0]] = kv.length > 1 ? URLDecoder.decode( kv[1], "UTF-8" ) : ""
			}
		}

		return params
    }

    /**
     * helper for building link for popup window
     **/
	def static createWindowLink( def config, def type, def params = [:], def sealed = true ) {
		// retrieve parameter from config
		def typeConfig = config.api."${type}"
		def url  = typeConfig.window.url
		def key  = config.accesskey
		def test = config.testmode ?: 1

		// add testmode to params
		if( test == 1 || test == "1" )
			params.testmode="1"
		else
			params.testmode = null

		// build parameter string
		def p = new StringBuilder()		
		params.eachWithIndex { k, v, i ->
		    // skip empty values
			if( v ) {			
    			p.append( "${k}="+URLEncoder.encode( "${v}", "UTF-8" ) )
    			// skip last amp
    			if( i < params.size()-1 )
    				p.append( "&" )
    		}
		}
		
		// create seal
		def seal = null
		if( sealed )
			seal = (p + key).encodeAsMD5()

		// full url
		return "${url}/?${p}" + (seal ? "&seal=${seal}" : "")
	}
	
}