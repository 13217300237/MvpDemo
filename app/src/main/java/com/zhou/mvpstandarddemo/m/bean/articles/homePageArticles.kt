package com.zhou.mvpstandarddemo.m.bean.articles

/**
 * 新闻数据的json解析bean
 */
class ArticleBean {
    var data: Data? = null
    var errorCode = 0
    var errorMsg: String? = null

    override fun toString(): String {
        return "data:${data.toString()}"
    }
}

class Datas {
    var apkLink: String? = null
    var audit = 0
    var author: String? = null
    var canEdit = false
    var chapterId = 0
    var chapterName: String? = null
    var collect = false
    var courseId = 0
    var desc: String? = null
    var descMd: String? = null
    var envelopePic: String? = null
    var fresh = false
    var id = 0
    var link: String? = null
    var niceDate: String? = null
    var niceShareDate: String? = null
    var origin: String? = null
    var prefix: String? = null
    var projectLink: String? = null
    var publishTime: Long = 0
    var selfVisible = 0
    var shareDate: Long = 0
    var shareUser: String? = null
    var superChapterId = 0
    var superChapterName: String? = null
    var title: String? = null
    var type = 0
    var userId = 0
    var visible = 0
    var zan = 0

    override fun toString(): String {
        return "title:$title,id:$id"
    }
}

class Data {
    var curPage = 0
    var datas: List<Datas>? = null
    var offset = 0
    var over = false
    var pageCount = 0
    var size = 0
    var total = 0

    override fun toString(): String {
        return "curPage:$curPage,datas:${datas.toString()}"
    }
}