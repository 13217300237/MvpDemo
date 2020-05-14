package com.zhou.mvpdemo.m.bean.register

/**
 * Copyright 2020 bejson.com
 */

/**
 * Auto-generated: 2020-05-14 16:54:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
class RegisterBean {
    var data: Data? = null
    var errorCode = 0
    var errorMsg: String? = null


    override fun toString(): String {
        return "[data:$data,errorCode:$errorCode,errorMsg:$errorMsg]"
    }
}

/**
 * Copyright 2020 bejson.com
 */


/**
 * Auto-generated: 2020-05-14 16:54:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
class Data {
    var admin = false
    var chapterTops: List<String>? = null
    var collectIds: List<String>? = null
    var email: String? = null
    var icon: String? = null
    var id = 0
    var nickname: String? = null
    var password: String? = null
    var publicName: String? = null
    var token: String? = null
    var type = 0
    var username: String? = null

    override fun toString(): String {
        return "[username:$username,nickname:$nickname]"
    }
}