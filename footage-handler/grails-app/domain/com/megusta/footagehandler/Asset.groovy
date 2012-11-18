package com.megusta.footagehandler

class Asset {

    static transients = ["tmpfile"]

    String title
    String description
    Long lastModified
    Double percentTransferred
    Long bytesPerSecond
    Long bytesTransfered
    Long length
    String localPath
    String extension

    Date dateCreated
    Date lastUpdated

    //transient props
    File tmpfile

    static constraints = {
        title(nullable: true)
        extension(nullable: false)
        description(nullable: true)
        percentTransferred(nullable: true)
        length(nullable: true)
        localPath(nullable: true)
        lastModified(nullable: true)
        description(nullable: true)
        bytesPerSecond(nullable: true)
        bytesTransfered(nullable: true)
    }

    Asset() {}

    Asset(File file) {
        newFile(file)
    }

    //method to setup s3 asset for upload with custom options for upload

    def newFile(File file) {
        this.tmpfile = file;
        this.length = file.length();
        this.lastModified = file.lastModified();
        this.localPath = this.tmpfile.canonicalPath;
    }

}
