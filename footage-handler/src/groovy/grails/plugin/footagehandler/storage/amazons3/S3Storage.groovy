package grails.plugin.footagehandler.storage.amazons3

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import grails.plugin.footagehandler.storage.Storage

class S3Storage extends Storage {

    //Add the right behaviors to the service
    def S3Storage() {

        def config = getConfig()

        this.putFileBehavior = new S3PutFileBehavior(config)
        this.putFileStreamBehavior = new S3PutFileStreamBehavior(config)
        this.getFileBehavior = new S3GetFileBehavior(config)
        this.getFileStreamBehavior = new S3GetFileStreamBehavior(config)
        this.removeBehavior = new S3RemoveBehavior(config)

    }

    private S3Config getConfig() {
        new S3Config( bucket:     CH.config.grails.plugin.aws.bucket,
                      accessKey:  CH.config.grails.plugin.aws.accessKey,
                      secretKey:  CH.config.grails.plugin.aws.secretKey,
                      serverUrl:  CH.config.grails.plugin.aws.serverUrl)
    }

}
