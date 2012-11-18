package grails.plugin.footagehandler.storage.amazons3

import grails.plugin.footagehandler.storage.RemoveBehavior
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client


class S3RemoveBehavior implements RemoveBehavior{

    S3Config config

    public S3RemoveBehavior(S3Config config){
        this.config = config
    }

    /**
     * Removes file from AWS S3.
     *
     * @param key the key of the object to delete
     * @return true if file was removed successfully, false otherwise
     */
    def execute(String key) {
        AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(config.accessKey, config.secretKey))
        s3Client.deleteObject(config.bucket, key)
    }

}
