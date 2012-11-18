includeTargets << grailsScript("Init")

target(main: "The description of the script goes here!") {
    // TODO: Implement script here
}

setDefaultTarget(main)
includeTargets << new File("$basedir/scripts/InsertText.groovy")

def insertInstance = new InsertText(    "$basedir/grails-app",
                                        "conf/Config.groovy",
                                        "grails.plugins.megusta.Validators.password")

def input = """
//added by the useValidators script
    grails.plugins.megusta.Validators.password.minLetter = true
    grails.plugins.megusta.Validators.password.minNumber = true
    grails.plugins.megusta.Validators.password.minChars = 8
    grails.plugins.megusta.Validators.password.allowAdmin = false
    grails.plugins.megusta.Validators.password.unequalUsername = true
    grails.plugins.megusta.Validators.password.passwordAgain = true
    grails.plugins.megusta.Validators.email.unique = true
    grails.plugins.megusta.Validators.username.unique = true    
"""
insertInstance.putTextOnce(input)

def insertInstance2 = new InsertText(    "$basedir/grails-app",
                                        "i18n/messages.properties",
                                        "password.must.contain.letter")

def input2 = """
//added by the megusta useValidators script
password.must.contain.letter = password must contain at least one letter, better more
password.must.contain.number = password must contain at least one number, better more
password.cannot.be.username = password cannot be the same as username
password.must.have.at.least.chars = password is too short, at least {0} chars
passwords.do.not.match = password do not match
email.is.not.unique = email is not unique
"""
insertInstance2.putTextOnce(input2)  