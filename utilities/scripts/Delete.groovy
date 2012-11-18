includeTargets << grailsScript("Init")

target(main: "The description of the script goes here!") {
    // TODO: Implement script here
}

setDefaultTarget(main)

def basePath        = "C:\\Users\\dell\\"
def ivyPath         = ".ivy2\\cache\\"
def projectsPath    = ".grails\\1.3.7\\projects"
def GroupId         = "org.grails.plugins\\"
def Pluginname      = "utilities"
def version         = "-0.4"

def ivyString = basePath + ivyPath + GroupId + Pluginname
def ivy = new java.io.File(ivyString)
ivy.deleteDir()

def projectsString = basePath + projectsPath
def projects = new java.io.File(projectsString)

def file 
projects.eachDir { outer->
    file = new java.io.File (outer, "plugins\\" + Pluginname + version)   
    file.deleteDir()
}
