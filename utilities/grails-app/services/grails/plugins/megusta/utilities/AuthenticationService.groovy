package grails.plugins.megusta.utilities

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder

class AuthenticationService {

    static transactional = true

    def springSecurityService

    def createLink (def action, def controller, def params) {
        def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
        def url = g.createLink( action: action,
                                controller: controller,
                                absolute: true,
                                params: params)
        return url
    }

    def saveUser(def fieldList){
        def constructList = createCustructList(fieldList)

        def userClassName = ConfigurationHolder.config.grails.plugins.springsecurity.userLookup.userDomainClassName
        def userClass = ApplicationHolder.application.getClassForName(userClassName)

        def userInstance = userClass.newInstance(constructList)
        userInstance.save(flush: true)

        return userInstance
    }
	
	def updateUser(def fieldList){
		def constructList = createCustructList(fieldList)
		
		def userClassName = ConfigurationHolder.config.grails.plugins.springsecurity.userLookup.userDomainClassName
		def userClass = ApplicationHolder.application.getClassForName(userClassName)
		
		def userId = constructList.get('id')
		println("constructList.get('id')="+userId)
		
		def userInstance = userClass.findById(userId)
		userInstance.properties = constructList
		userInstance.save()

		println(userInstance)
	}

    def createCustructList (def fieldList) {
        def constructList = [:]

        fieldList.each {
            if (it.key == "password") {
                def password = springSecurityService.encodePassword(it.value)
                constructList.put(it.key, password)
            } else {
                if (it.value != null) {
                    constructList.put(it.key, it.value)
                }
            }
        }
        return constructList
    }

    void giveUserRole(def userInstance, def roleString) {
        def roleClassName = ConfigurationHolder.config.grails.plugins.springsecurity.authority.className
        def userRoleClassName = ConfigurationHolder.config.grails.plugins.springsecurity.userLookup.authorityJoinClassName

        def roleClass = ApplicationHolder.application.getClassForName(roleClassName)
        def userRoleClass = ApplicationHolder.application.getClassForName(userRoleClassName)

        def role = roleClass.findByAuthority(roleString)
        def userRole = userRoleClass.newInstance(role: role, user: userInstance)
        if (role == null ) throw new Exception ("role.not.found")

        userRole.save(flush: true)
    }

    void saveNewToken (def userInstance, def fieldString){
        userInstance."${fieldString}" = createToken()
        userInstance.save(flush: true)
    }

    String createToken () {
        String tokenUncoded = "" + new Date() + Math.random()
        return tokenUncoded.encodeAsMD5()
    }

    def prooveToken (def tokenField, def paramsToken, def username) {
        def userClassName = ConfigurationHolder.config.grails.plugins.springsecurity.userLookup.userDomainClassName
        def userClass = ApplicationHolder.application.getClassForName(userClassName)

        def userInstance = userClass.findByUsername(username)

        if (userInstance != null && userInstance."${tokenField}".equals(paramsToken)) {
            return true
        } else {
            return false
        }
    }

    void enableUser(def username) {
        def userClassName = ConfigurationHolder.config.grails.plugins.springsecurity.userLookup.userDomainClassName
        def userClass = ApplicationHolder.application.getClassForName(userClassName)
        def userInstance = userClass.findByUsername(username)

        userInstance.enabled = true
        userInstance.save(flush: true)
    }

    void changePassword(def newPassword, def username) {
        def userClassName = ConfigurationHolder.config.grails.plugins.springsecurity.userLookup.userDomainClassName
        def userClass = ApplicationHolder.application.getClassForName(userClassName)

        def userInstance = userClass.findByUsername(username)
        userInstance.password = springSecurityService.encodePassword(newPassword)
        userInstance.save(flush: true)
    }

}
