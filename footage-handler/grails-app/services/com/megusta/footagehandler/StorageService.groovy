package com.megusta.footagehandler

import grails.plugin.footagehandler.storage.Storage
import grails.plugin.footagehandler.storage.StorageServices
import grails.plugin.footagehandler.storage.amazons3.S3Storage
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class StorageService {

    static transactional = true

    S3Asset transferToAS3(String fileName) {
        String localStoragePath = ConfigurationHolder.config.com.megusta.footagehandler.localAssetsPath
        File file = new File(localStoragePath + "/$fileName")
        this.transfer(file) as S3Asset
    }

    /**
     * Transfers a file with a specific key to the desired storage service (default amazonS3). The key can contain subfolders
     * in wich the actual file should be stored.
     *
     * @param file
     * @param key
     * @param storageType
     * @return
     */
    def transfer(File file, String key, StorageServices storageType = StorageServices.AMAZON_S3) {
        Storage storageService = getStorage(storageType)
        Asset asset = storageService.putFile(file, key)
    }

    def transfer(File file, StorageServices storageType) {
        Storage storageService = getStorage(storageType)
        Asset asset = storageService.putFile(file)
    }

    /**
     * Call the transfer function using amazon-s3 as the default storage service
     * @param file
     * @return
     */
    def transfer(File file) {
        Storage storage = getStorage(StorageServices.AMAZON_S3)
        Asset asset = storage.putFile(file)
    }

    /**
     * Returns the appropriate service instance based on the desired storage service.
     */
    private Storage getStorage(StorageServices storageType) {
        switch (storageType) {
            case StorageServices.AMAZON_S3 :
                return new S3Storage()
        }
        throw new RuntimeException("Uninplemented storage interface.")
    }



}
