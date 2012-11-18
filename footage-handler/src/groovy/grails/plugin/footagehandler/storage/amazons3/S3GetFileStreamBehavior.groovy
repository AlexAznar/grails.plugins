package grails.plugin.footagehandler.storage.amazons3

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GetObjectRequest
import grails.plugin.footagehandler.storage.GetFileStreamBehavior

class S3GetFileStreamBehavior implements GetFileStreamBehavior {

    S3Config config

    public S3GetFileStreamBehavior(S3Config config) {
        this.config = config
    }

    /**
     * Gets file stream from storage.
     *
     * @param key the key under which the desired object is stored
     */
    public def execute(String key) {
        AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(config.accessKey, config.secretKey))
        return s3Client.getObject(new GetObjectRequest(config.bucket, key)).getObjectContent()
    }

}
