package shop

import com.github.jknack.handlebars.*
import tenanttheme.*

class ShoptestController {
    def shopService
	def tenantService
	def themeService
    def groovyPagesTemplateEngine

    def index() {
        // get first catalog
        [catalog: Catalog.list()[0] ]
    }

    def testdata() {
        shopService.createTestdata()
	 	tenantService.createTestdata()
        render 'created'
    }

	def tpllist() {
		def ps = Product.list()

		def li = []
		ps.each { p ->
			li << [link:g.createLink(controller:'shoptest',action:'tpldetail',id:p.id),product:p]
		}
		
		render themeService.render( tenant: request.tenant, theme: new Theme(name:'coco'), template: 'product/list', model: [products:li] )
	}
	def tpldetail() {
		def ps = Product.findById( params.id ?: 1)
		
		render themeService.render( tenant: request.tenant, theme: new Theme(name:'coco'), template: 'product/detail', model: [product:ps,backlink:g.createLink(controller:'shoptest',action:'tpllist')] )
	}
    
    def tpl() {
        def tpl2 = '''
            <html>
            <body>
            <h1>GSP Template Engine</h1>
            <p>This is just a sample with template text.</p>
            <g:if test="${show}"><p>We can use taglibs in our template!</p></g:if>
            <ul>
            <g:each in="${items}" var="item">
            <li>${item}</li>
            </g:each>
            </ul>

            <b>a: <%= new File('/tmp/asdf').text %></b>
            </body>
            </html>
        '''
        
        def tpl = '''
            <html>
            <body>
            <h1>Handlebars2 Template Engine</h1>
            <p>This is just a sample with template text.</p>
			<h2>{{kuck}}</h2>
            <ul>
            {{#each items}}
                <li>{{kuck}}{{@index}} {{#if @last}}letzter{{/if}} - {{name}} ({{age}})</li>
            {{/each}}
            </ul>

			{{#if kuck}}
			{{#link}}go to start{{/link}}
			{{/if}}

			<h3>Tenant</h3>
			{{tenant.name}}
			- {{tenant.config.mobile}}
            - {{tenant.config.address}}
			{{#each tenant.domains}}
			    <p>{{.}}</p>
			{{/each}}
			<hr/>
            </body>
            </html>
        '''

//        def output = new StringWriter()
//        groovyPagesTemplateEngine.createTemplate(tpl, 'sample').make([show: true, items: ['Grails','Groovy']]).writeTo(output)
//        render output.toString()

        Handlebars handlebars = new Handlebars();
		handlebars.setExposePseudoVariables(true);
        Template template = handlebars.compile(tpl);

		def gg = g
		def clj = new Lambda<String,Object>() {
		    public String apply(def scope, Template templatex) {
			println scope
		     return """<a href="${gg.createLink(controller:'shoptest',action:'index',body:'huhu')}">${templatex.apply(scope)}</a>""";
		    }
		}

        render template.apply([tenant:request.tenant,link:clj,kuck:'servus',items:[[kuck:'bienvenido',name:'chris',age:25],[kuck:'hola',name:'isa',age:20]]]);
    }
}
