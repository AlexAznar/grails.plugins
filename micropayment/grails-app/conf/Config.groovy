// configuration for plugin testing - will not be included in the plugin zip

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}


// put in your app config
grails.plugins.micropayment.account   = "12345"
grails.plugins.micropayment.project   = "myprj"
grails.plugins.micropayment.accesskey = "23h9f8h3498fh3489fh349f7hg7g"
grails.plugins.micropayment.testmode  = "1"
// prepay
grails.plugins.micropayment.api.prepay.url        = "https://webservices.micropayment.de/public/prepay/v1.0/nvp"
grails.plugins.micropayment.api.prepay.window.url = "https://billing.micropayment.de/prepay/event"
// debit
grails.plugins.micropayment.api.debit.url        = "https://webservices.micropayment.de/public/debit/v1.0/nvp"
grails.plugins.micropayment.api.debit.window.url = "https://billing.micropayment.de/lastschrift/event"
// ebank2pay
grails.plugins.micropayment.api.ebank2pay.url        = "https://webservices.micropayment.de/public/ebank2pay/v1.0/nvp"
grails.plugins.micropayment.api.ebank2pay.window.url = "https://billing.micropayment.de/ebank2pay/event"
// creditcard
grails.plugins.micropayment.api.creditcard.url        = "https://webservices.micropayment.de/public/creditcard/v1.0/nvp"
grails.plugins.micropayment.api.creditcard.window.url = "https://billing.micropayment.de/creditcard/event"

grails.plugins.micropayment.defaults = [:]