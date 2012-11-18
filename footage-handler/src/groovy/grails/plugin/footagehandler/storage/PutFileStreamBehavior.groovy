package grails.plugin.footagehandler.storage

import com.megusta.footagehandler.Asset

public interface PutFileStreamBehavior {

    /**
     * Stores data from a stream to Amazon S3.
     *
     * @param stream the stream containing the data to be uploaded to Amazon S3
     * @param key the key under which to store the specified stream
     */
    Asset execute(def stream, String key)

}