package com.megusta.footagehandler

import org.codehaus.groovy.grails.commons.ConfigurationHolder


class S3Asset extends Asset implements Serializable {

    static transients = ["tmpfile"]

    static mapping = {
        version false
        cache Boolean.valueOf(ConfigurationHolder.config.aws.disableHibernateCaching ?: false)
        key column: 'aws_key'
        status column: 'aws_status', index: 'idx_asset_status'
    }

    public static final String STATUS_BLANK = "blank"
    public static final String STATUS_TO_BE_REMOVED = "to-be-removed"
    public static final String STATUS_REMOVED = "removed"
    public static final String STATUS_NEW = "new"
    public static final String STATUS_INPROGRESS = "inprogress"
    public static final String STATUS_HOSTED = "hosted"
    public static final String STATUS_MISSING_FILE = "missing-local-file"
    public static final String STATUS_ERROR = 'error'

    public static final String SEPARATOR = "."

    boolean approved = false;

    String key

    /*
   The bucket to store this resource in. It will be prefixed with your S3 access key
   when uploaded to to S3 to ensure it is unique.
    */
    String bucket

    String protocol = "http://"
    String status = STATUS_BLANK

    String hostName // the host name of the server that created the asset
    String url


    static constraints = {
        key(unique: true)
        hostName(nullable: true)
        approved(nullable: true)
    }

    S3Asset() {}

    S3Asset(File file) {
        super(file)
    }

    String toString() {
        return "Asset ${id}"
    }

    def beforeInsert = {
        try {
            hostName = InetAddress.getLocalHost().getHostName()
        } catch (java.net.UnknownHostException uhe) {
        }
    }

}
