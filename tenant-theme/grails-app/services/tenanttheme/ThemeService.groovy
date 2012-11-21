package tenanttheme

import com.github.jknack.handlebars.*
import tenantcore.*


class ThemeService {

	// injected properties
	def tenantService
	def grailsApplication

	/**
	 * render
	 *
	 * Examples:
	 * themeService.render template: 'product/detail'
	 *
	 *
	 *
	 *
	 **/
	public def render( Map options = [:], def closure = null ) {
		def tenant   = options.tenant
		def theme    = options.theme
		def layout   = options.layout
		def template = options.template ?: options.view
		def model    = options.model ?: closure ? closure.call() : [:]

		// do handlebars parsing
        Handlebars handlebars = new Handlebars();
		handlebars.setExposePseudoVariables(true);

        Template compiledTemplate = handlebars.compile( loadTemplate(tenant,theme,template) );

		return compiledTemplate.apply(model);
	}
	
	public def loadTemplate( def tenant, Theme theme, String template ) {
		def config = getPluginConfig()
		def path   = getPathResolver()
		def file   = new File( config.themes.directory + path(tenant,theme) + "/templates/${template}.html" )
		if( !file.exists() ) {
			log.error("Template not found: " + file.absolutePath)
			throw new Exception("Template not found: " + file.absolutePath ) 
		}
		return file.text
	}

	public def getPathResolver() {
		getPluginConfig().themes.pathResolver ?: { tenant, theme -> "/${tenant.name}/${theme.name}" }
	}

    /**
     * initializeWithConfig
     *
     * takes a configuration Map (or the config)
     *
     * @param config
     */
    public void initializeWithConfig( def config = null ) {
        // if no config is supplied try to get it from the app config
        if( !config ) {
            config = getPluginConfig()
        }
		if( !config )
			throw new Exception("TenantTheme configuration is required in application Config.groovy")

		// get and create directory if necessary
		def dir = new File(config.themes.directory)
		if( !dir.exists() ) {
			dir.mkdirs()
			log.warn( "TenantTheme directory not exists. Created: ${dir.absolutePath}" )			
		}

    }

	public def getPluginConfig() {
		return grailsApplication.config.grails?.plugins?.tenanttheme	
	}
}
