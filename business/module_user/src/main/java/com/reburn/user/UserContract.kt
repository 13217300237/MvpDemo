package com.reburn.user

import com.reburn.user.m.UserModel
import com.reburn.user.p.UserPresenter
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
class UserContract {
    interface Model : BaseModel {
        fun getUserNameFromNet(): String
    }

    interface Presenter : BasePresenter<BaseView> {
        fun updateUserName()
    }

    interface View : BaseView {
        fun updateUserNameText(str: String)
    }

    companion object {
        fun getModel(): Model {
            return UserModel()
        }

        fun getPresenter(v: View): Presenter {
            return UserPresenter(v)
        }

    }
}