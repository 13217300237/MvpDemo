package com.zhou.app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 *    author : ZhouHang
 *    e-mail : zhouhang@enfry.com
 *    date   : 2020/8/10_15:01
 *    desc   :
 *    version: 1.0
 */
class AppMain : Application() {
    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}