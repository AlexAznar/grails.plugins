package grails.plugin.footagehandler.storage

import com.megusta.footagehandler.Asset


public interface GetFileBehavior {

    /**
     * Gets file from Amazon S3.
     *
     * @param key the key under which the desired object is stored
     * @param file destination file
     * @return file if it was found, null otherwise
     */
    public Asset execute(String key, File file)

}