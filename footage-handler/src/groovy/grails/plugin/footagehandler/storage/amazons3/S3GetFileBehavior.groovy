package grails.plugin.footagehandler.storage.amazons3

import grails.plugin.footagehandler.storage.GetFileBehavior
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.AmazonS3Client
import com.megusta.footagehandler.Asset
import com.megusta.footagehandler.S3Asset
import com.amazonaws.auth.BasicAWSCredentials


class S3GetFileBehavior implements GetFileBehavior {

    S3Config config

    public S3GetFileBehavior(S3Config config) {
        this.config = config
    }

    /**
     * Gets file from Amazon S3.
     *
     * @param key the key under which the desired object is stored
     * @param file destination file
     * @return file if it was found, null otherwise
     */
    public Asset execute(String key, File file) {

        AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(config.accessKey, config.secretKey))

        def asset = S3Asset.findByKey(key)
        s3Client.getObject(new GetObjectRequest(config.bucket, key), file)
        asset.tmpFile = file
        return asset
    }

}
