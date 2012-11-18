package com

import grails.plugins.megusta.utilities.Validators

class UseController {
    def selectService

    def index = {
        def userCommandInstance = new UsageCommand(email: "ich@ich.de", password: "öööööööööööööööööööök1", username: "hi")
        userCommandInstance.validate()

        def test = new usageTbl(email: "hi")
        test.validate()

        return [instance: test]
    }

    def save = {UsageCommand usageCommand ->

    }

    def testCreateSelects = {
        def test = new UsageCommand()

        render selectService.createNumberSelectEntries (test,"age", message(code: 'selects.default.message'))
        render selectService.createNumberSelectEntries (test,"age2", message(code: 'selects.default.message'))
    }

    def createSelects = {
        def usageInstance = new usageTbl (name: 'hallo', email: "hi")
        usageInstance.save(flush: true)
        println 'now ' + usageInstance.count() + ' instances of usage'

        def result = selectService.createDomainSelects(usageTbl, usageTbl)
        result = selectService.addNumberSelect(new UsageCommand(), result, "age")
        result = selectService.addNumberSelect(new UsageCommand(), result, "age2", "na was solls sein")
        render result
    }

}

class UsageCommand {
    String password
    String passwordAgain
    String username
    String email
    Integer age
    Integer age2

    static constraints = {
        password blank: false, nullable: false, validator: Validators.passwords
        email (nullable: false, blank: false, email:true, validator: Validators.emailUnique)
        age min: 40, max:80
        age2 range:30..90
    }

}