package grails.plugins.megusta.utilities
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder

class CheckBoxService {

    static transactional = true

     def convertCheckBoxesToStringArray(def input)
    {
        if(input==null)
        {
            return []
        }
        if(input instanceof String)
        {
            println input.length()
            return [input]
        }
        return input

    }
    def convertStringArrayToLongArray(def stringArray)
    {
        def longArray = []
        stringArray.each {
            longArray.add(Long.parseLong(it))
        }
        return  longArray
    }
    def convertCheckBoxesToLongArray (def input)
    {
        def stringArray = convertCheckBoxesToStringArray(input)
        return  convertStringArrayToLongArray(stringArray)
    }
    def convertCheckBoxesToUserArray(def input)
    {
        def userClassName = ConfigurationHolder.config.grails.plugins.springsecurity.userLookup.userDomainClassName
        def userClass = ApplicationHolder.application.getClassForName(userClassName)


        def longIdArray = convertCheckBoxesToLongArray(input)
       if(longIdArray.size()!=0){
             def userArray = userClass.withCriteria {
            'in'("id",longIdArray)
            }
            return userArray
           }
        return []
    }
}
