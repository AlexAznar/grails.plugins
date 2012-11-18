package grails.plugins.megusta.utilities

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder


class Validators {
    
    static def holder = ConfigurationHolder.getConfig() 
    
    static def minLetter = holder.grails.plugins.megusta.Validators.password.minLetter
    static def minNumber = holder.grails.plugins.megusta.Validators.password.minNumber 
    static def minChars = holder.grails.plugins.megusta.Validators.password.minChars
    static def allowAdmin = holder.grails.plugins.megusta.Validators.password.allowAdmin
    static def unequalUsername = holder.grails.plugins.megusta.Validators.password.unequalUsername
    static def passwordAgain = holder.grails.plugins.megusta.Validators.password.passwordAgain
    static def userClassName = holder.grails.plugins.springsecurity.userLookup.userDomainClassName
    static def emailUniqueConfig = holder.grails.plugins.megusta.Validators.email.unique 
    static def usernameUniqueConfig = holder.grails.plugins.megusta.Validators.username.unique  

    static passwords = {password, userCommandInstance, errorObject ->
        passwordNotUsername(password, userCommandInstance, errorObject)
        passwordMinChar(password, userCommandInstance, errorObject)
        passwordNumber(password, userCommandInstance, errorObject)
        passwordLetter(password, userCommandInstance, errorObject)
        passwordsMatch(password, userCommandInstance, errorObject)
    }

    static passwordNotUsername (password, userCommandInstance, errorObject){
        if (doCheck(password) && unequalUsername == true && password == userCommandInstance.properties['username']) {
            errorObject.rejectValue("password", "password.cannot.be.username", "password.cannot.be.username")
        }
        return true
    }

    static passwordMinChar (password, userCommandInstance, errorObject){
        if (doCheck(password) && password.size() < minChars ) {
            errorObject.rejectValue("password", "password.must.have.at.least.chars", minChars as Object[], "password.must.have.at.least.chars")
        }
        return true
    }

    static passwordNumber (password, userCommandInstance, errorObject){
        if (doCheck(password) && minNumber == true && !password.matches('^.*\\p{Digit}.*$')) {
            errorObject.rejectValue("password", "password.must.contain.number", "password.must.contain.number")
        }
        return true
    }

    static passwordLetter (password, userCommandInstance, errorObject){
        if (doCheck(password) && minLetter == true && !password.matches('^.*\\p{Alpha}.*$')) {
            errorObject.rejectValue("password", "password.must.contain.letter", "password.must.contain.letter")
        }
        return true
    }

    static passwordsMatch (password, userCommandInstance, errorObject){
        if (doCheck(password) && passwordAgain == true && password != userCommandInstance.properties['passwordAgain']) {
            errorObject.rejectValue("password", "passwords.do.not.match", "passwords.do.not.match")
        }
        return true
    }

    static def doCheck(def password) {
        return !(password == 'p1' && allowAdmin == true)
    }

    static emailUnique = {field, userCommandInstance, errorObject ->
        if (emailUniqueConfig){
            def userClass = ApplicationHolder.application.getClassForName(userClassName)
            def result = userClass.findAllByEmail(field)
            def number = result.size
            if (number >= 1) {
                errorObject.rejectValue("email", "email.is.not.unique", "email.is.not.unique")
            }
        }
    }

    static usernameUnique = {field, userCommandInstance, errorObject ->
        if (usernameUniqueConfig) {
            def userClass = ApplicationHolder.application.getClassForName(userClassName)
            def result = userClass.findAllByUsername(field)
            def number = result.size
            if (number >= 1) {
                errorObject.rejectValue("username", "username.is.not.unique", "username.is.not.unique")
            }
        }
    }

}



