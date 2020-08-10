package com.reburn.module_user

import com.reburn.module_businiess_mode.module_user.m.BusinessModelingModel
import com.reburn.module_businiess_mode.module_user.p.BusinessModelingPresenter
import com.zhou.baselibrary.m.BaseModel
import com.zhou.baselibrary.p.BasePresenter
import com.zhou.baselibrary.v.BaseView

/**
 *    author : ZhouHang
 *    e-mail : zhouhang@enfry.com
 *    date   : 2020/8/10_15:54
 *    desc   :
 *    version: 1.0
 */
class BusinessModelingContract {
    interface Model : BaseModel {
        fun getMsgFromNet(): String
    }

    interface Presenter : BasePresenter<BaseView> {
        fun updateMsg()
    }

    interface View : BaseView {
        fun updateMsgText(str: String)
    }

    companion object {
        fun getModel(): Model {
            return BusinessModelingModel()
        }

        fun getPresenter(v: View): Presenter {
            return BusinessModelingPresenter(v)
        }

    }
}