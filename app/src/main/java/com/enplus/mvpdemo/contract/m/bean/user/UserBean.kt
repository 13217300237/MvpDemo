package com.enplus.mvpdemo.contract.m.bean.user


/**
 * 支持登录和注册的数据转换
 */
class UserBean {
    var data: Data? = null
    var errorCode = 0
    var errorMsg: String? = null

    override fun toString(): String {
        return "[data:$data,errorCode:$errorCode,errorMsg:$errorMsg]"
    }
}


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