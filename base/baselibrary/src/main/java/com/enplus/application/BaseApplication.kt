package com.enplus.application

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.enplus.mvp.BuildConfig

/**
 *    author : ZhouHang
 *    e-mail : zhouhang@enfry.com
 *    date   : 2020/8/10_15:01
 *    desc   :
 *    version: 1.0
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    companion object {
        var instance: BaseApplication? = null
            private set
    }
}