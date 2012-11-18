package grails.plugins.megusta.utilities

class SelectService {

    static transactional = true

    def createDomainSelects (def ... params) {
        def result = [:]
        def name, list

        params.each {
            result.put (it.getSimpleName(), it.list() )
        }

        return result
    }

    def addNumberSelect (def domainOrCommand, def domainMap, def field) {
        return addNumberSelect(domainOrCommand, domainMap, field, "Please Select")
    }

    def addNumberSelect (def domainOrCommand, def domainMap, def field, def selectString) {
        def name = field
        def list = createNumberSelectEntries(domainOrCommand, field, selectString)
        domainMap.put (name, list)

        return domainMap
    }

    def createNumberSelectEntries (def domainOrCommand, def field) {
        createNumberSelectEntries(domainOrCommand, field, "Please Select")
    }

    def createNumberSelectEntries (def domainOrCommand, def field, def selectString) {
       if (domainOrCommand.constraints.get(field).hasAppliedConstraint("range")) {
            return createRangeSelectEntries (domainOrCommand, field, selectString)
       }

       if (domainOrCommand.constraints.get(field).hasAppliedConstraint("min")) {
            return createMinMaxSelectEntries (domainOrCommand, field, selectString)
        }
    }

    def createMinMaxSelectEntries (def domainOrCommand, def field, def selectString) {
        def min = domainOrCommand.constraints.get(field).getAppliedConstraint('min').getMinValue()
        def max = domainOrCommand.constraints.get(field).getAppliedConstraint('max').getMaxValue()

        return constructList(min, max, selectString)
    }

    def createRangeSelectEntries (def domainOrCommand, def field, def selectString) {
        def min = domainOrCommand.constraints.get(field).getAppliedConstraint('range').getRange().getFrom()
        def max = domainOrCommand.constraints.get(field).getAppliedConstraint('range').getRange().getTo()

        return constructList(min, max, selectString)
    }

    def constructList(def min, def max, def selectString) {
        def returnValue = new java.util.ArrayList()
        returnValue.add(selectString)

        for (int i = min; i <= max; i++) {
            returnValue.add(i)
        }

        return returnValue
    }

}
