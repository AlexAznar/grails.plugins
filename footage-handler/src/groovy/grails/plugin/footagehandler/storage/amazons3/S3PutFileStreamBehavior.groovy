package grails.plugin.footagehandler.storage.amazons3

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GroupGrantee
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.Permission
import com.megusta.footagehandler.FileUtils
import com.megusta.footagehandler.S3Asset
import grails.plugin.footagehandler.storage.PutFileStreamBehavior

class S3PutFileStreamBehavior implements PutFileStreamBehavior {

    S3Config config

    public S3PutFileStreamBehavior(S3Config config) {
        this.config = config
    }

    /**
     * Execute the put action using an S3Asset
     * @param asset S3Asset
     */
    S3Asset execute(def stream, String key) {

        AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(config.accessKey, config.secretKey))

        def asset = new S3Asset()
        asset.extension = FileUtils.getFileExtension(key)
        def acl = s3Client.getObjectAcl(config.bucket, key)

        s3Client.putObject(config.bucket, key, stream, new ObjectMetadata())

        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read)
        s3Client.setObjectAcl(config.bucket, key, acl)

        s3Client.setObjectAcl(config.bucket, key, acl)
        asset.bucket = config.bucket
        asset.key = key
        asset.url = config.bucketLink +  "/" + key
        return asset.save(flush:true)
    }

}
