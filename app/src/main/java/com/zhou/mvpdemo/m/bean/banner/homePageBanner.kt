package com.zhou.mvpdemo.m.bean.banner

class BannerBean {
    var data: List<Data>? = null
    var errorCode = 0
    var errorMsg: String? = null

    override fun toString(): String {
        return data.toString()
    }
}

class Data {
    var desc: String? = null
    var id = 0
    var imagePath: String? = null
    var isVisible = 0
    var order = 0
    var title: String? = null
    var type = 0
    var url: String? = null

    override fun toString(): String {
        return "[id:$id,imagePath:$imagePath,title:$title]"
    }
}