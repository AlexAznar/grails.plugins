includeTargets << grailsScript("Init")

target(main: "The description of the script goes here!") {
    // TODO: Implement script here
}

setDefaultTarget(main)
includeTargets << new File("$basedir/scripts/InsertText.groovy")

def insertInstance = new InsertText( "$basedir/grails-app", "i18n/messages.properties", "grails.plugins.megusta.utilities.select.default")
def input = """
//added by the useSelects megusta script
grails.plugins.megusta.utilities.select.default = Please select
"""
insertInstance.putTextOnce(input)


