package grails.plugin.footagehandler.storage

import com.megusta.footagehandler.Asset

class Storage {

    PutFileBehavior putFileBehavior
    PutFileStreamBehavior putFileStreamBehavior
    GetFileBehavior getFileBehavior
    GetFileStreamBehavior getFileStreamBehavior
    RemoveBehavior removeBehavior

    /**
     * Gets file from storage
     *
     * @param key the key under which the desired object is stored
     * @param file destination file
     * @return asset if it was found, null otherwise
     */
    Asset getFile(String key, File file) {
        getFileBehavior.execute(key, file)
    }

    /**
     * Stores file to storage.
     *
     * @param file the file containing the data to be uploaded to Amazon S3
     * @param key the key under which to store the specified file
     */
    Asset putFile(File file, String key = "") {
        putFileBehavior.execute(file, key)
    }

    /**
     * Stores data from a stream to storage.
     *
     * @param stream the stream containing the data to be uploaded to Amazon S3
     * @param key the key under which to store the specified file, if the key is blank, then the system will generate a random one
     */
    Asset putFileStream(def stream, String key = "") {
        putFileStreamBehavior.execute(stream, key)
    }


    /**
     * Removes file from storage.
     *
     * @param key the key of the object to delete
     * @return true if file was removed successfully, false otherwise
     */
    void remove(String key) {
        removeBehavior.execute(key)
    }



}
