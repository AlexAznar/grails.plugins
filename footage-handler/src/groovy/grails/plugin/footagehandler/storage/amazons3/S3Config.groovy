package grails.plugin.footagehandler.storage.amazons3


class S3Config {

    String bucket
    String serverUrl
    String accessKey
    String secretKey

    def getBucketLink() {
        return this.serverUrl + "/" + this.bucket
    }

}
