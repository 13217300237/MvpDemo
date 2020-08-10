package com.enplus.reburn.modoleapp

import com.enplus.reburn.modoleapp.m.StructModel
import com.enplus.reburn.modoleapp.p.StructPresenter
import com.zhou.baselibrary.m.BaseModel
import com.zhou.baselibrary.p.BasePresenter
import com.zhou.baselibrary.v.BaseView

/**
 *    组件化：外壳modue的Contract MVP协议类
 *    author : ZhouHang
 *    e-mail : zhouhang@enfry.com
 *    date   : 2020/8/10_15:31
 *    desc   :
 *    version: 1.0
 */
class StructContract {
    interface Model : BaseModel
    interface Presenter : BasePresenter<BaseView>
    interface View : BaseView

    companion object {
        fun getModel(): Model {
            return StructModel()
        }

        fun getPresenter(v: View): Presenter {
            return StructPresenter()
        }

    }
}