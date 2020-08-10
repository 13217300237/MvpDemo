package com.enplus.reburn.struct

import com.enplus.reburn.struct.m.StructModel
import com.enplus.reburn.struct.p.StructPresenter
import com.enplus.mvp.m.BaseModel
import com.enplus.mvp.p.BasePresenter
import com.enplus.mvp.v.BaseView

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