package shop

import com.github.jknack.handlebars.*


class ShoptestController {
    def shopService
    def groovyPagesTemplateEngine

    def index() {
        // get first catalog
        [catalog: Catalog.list()[0] ]
    }

    def testdata() {
        shopService.createTestdata()
        render 'created'
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
            <h1>Handlebars Template Engine</h1>
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

        render template.apply([link:clj,kuck:'servus',items:[[kuck:'bienvenido',name:'chris',age:25],[kuck:'hola',name:'isa',age:20]]]);
    }
}
