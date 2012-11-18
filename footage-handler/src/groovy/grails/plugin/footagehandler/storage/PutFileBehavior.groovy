package grails.plugin.footagehandler.storage

import com.megusta.footagehandler.Asset


public interface PutFileBehavior {

    Asset execute(File File, String key)

}