package com.reburn.modeling

import com.reburn.modeling.m.BusinessModelingModel
import com.reburn.modeling.p.BusinessModelingPresenter
import com.enplus.mvp.m.BaseModel
import com.enplus.mvp.p.BasePresenter
import com.enplus.mvp.v.BaseView

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