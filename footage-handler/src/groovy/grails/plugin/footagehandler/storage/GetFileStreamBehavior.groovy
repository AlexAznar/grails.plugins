package grails.plugin.footagehandler.storage

public interface GetFileStreamBehavior {

    /**
     * Gets file stream from storage.
     *
     * @param key the key under which the desired object is stored
     */
    public def execute(String key)

}