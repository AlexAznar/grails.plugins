package com.megusta.footagehandler

import org.codehaus.groovy.grails.commons.ConfigurationHolder

/*
 This class allows for greater mapping control than a simple Map in S3Asset
*/
public class S3AssetOption implements Serializable {

    String name
    String value

    static belongsTo = [asset: S3Asset]

    static constraints = {
        name(nullable: false, size: 1..50)
        value(nullable: false, size: 1..200)
    }

    static mapping = {
        cache Boolean.valueOf(ConfigurationHolder.config.aws.disableHibernateCaching ?: false)
        //mapped to match legacy Map table
        table 's3asset_options'
        id composite: ['asset', 'name']
        asset column: 'options'
        name column: 'options_idx', index: 'option_name_idx', length: 50
        value column: 'options_elt', length: 200
    }
}
