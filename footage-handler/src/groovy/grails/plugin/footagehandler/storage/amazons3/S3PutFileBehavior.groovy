package grails.plugin.footagehandler.storage.amazons3

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GroupGrantee
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.Permission
import com.megusta.footagehandler.FileUtils
import com.megusta.footagehandler.S3Asset
import grails.plugin.footagehandler.storage.PutFileBehavior

class S3PutFileBehavior implements PutFileBehavior {

    static final RAND = new Random()

    S3Config config

    public S3PutFileBehavior(S3Config config) {
        this.config = config
    }

    /**
     * Execute the put action using an S3Asset
     * @param asset S3Asset
     */
    S3Asset execute(File file, String key = "") {

        def asset = new S3Asset(file)
        asset.extension = FileUtils.getFileExtension(file.canonicalPath)

        key = key == "" ? generateRandomKey(asset.extension) : key

        def metadata = new ObjectMetadata()
        def s3 = new AmazonS3Client(new BasicAWSCredentials(config.accessKey, config.secretKey))

        s3.putObject(config.bucket, key, new FileInputStream(file), metadata)

        def acl = s3.getObjectAcl(config.bucket,key)
        def grantee = GroupGrantee.AllUsers
        def permission = Permission.Read

        acl.grantPermission(grantee, permission)
        s3.setObjectAcl(config.bucket, key, acl)
        asset.bucket = config.bucket
        asset.key = key
        asset.url = config.bucketLink +  "/" + key

        S3Asset.withTransaction {
            asset = asset.save(flush:true)
        }

        return asset
    }

    private String generateRandomKey(String extension) {
        return new UUID(System.currentTimeMillis(),
                (System.currentTimeMillis() * System.currentTimeMillis() + RAND.nextInt(10000)) as long).toString() + '.' + extension
    }

}
