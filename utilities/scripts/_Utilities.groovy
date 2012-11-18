includeTargets << grailsScript("Init")

target(main: "The description of the script goes here!") {
    // TODO: Implement script here
}

setDefaultTarget(main)


class InsertText {
    def appDir
    def fileString
    def identifier
    
    InsertText (def appDirCon, def fileStringCon, def identifierCon) {
        appDir = appDirCon
        fileString = fileStringCon
        identifier = identifierCon        
    }
          
    def putTextOnce(def input) {
        if (!alreadyInstalled() ) {
            getFile().withWriterAppend {
                it.writeLine input
            }
        }    
    }
    
    def alreadyInstalled() {
        def result = 0
        getFile().eachLine({
            if (it.findAll(identifier).size() == 1) 
                result = result + 1
            
            if (it.findAll(identifier).size() > 1) 
                throw new Exception ("String.not.unique") 
        })
        if (result > 1) throw new Exception ("String.not.unique")
        if (result == 1) return true 
        if (result == 0) return false     
    }
    
    def getFile() {
        new File(appDir, fileString)   
    }
}
