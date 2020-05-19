package com.zhou.mvpdemo.contract

import com.zhou.baselibrary.m.BaseModel
import com.zhou.baselibrary.network.HttpCallback
import com.zhou.baselibrary.p.BasePresenter
import com.zhou.baselibrary.v.BaseView
import com.zhou.mvpdemo.contract.m.RegisterModel
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import com.zhou.mvpdemo.contract.p.RegisterPresenter

/**
 * 注册相关的MVP三层,都用接口约束起来
 */
class RegisterContract {
    //M
    interface Model : BaseModel {
        fun doRegister(
            username: String,
            pwd: String,
            rPwd: String,
            httpCallback: HttpCallback<UserBean>
        )
    }

    //V
    interface View : BaseView {
        fun handlerRegisterResult(data: UserBean?)
        fun getUsername(): String
        fun getPassword(): String
        fun getRePassword(): String
    }

    //P
    interface Presenter : BasePresenter<BaseView> {
        fun checkParams(): Boolean
        fun doRegister()
    }

    companion object {
        fun getPresenter(view: View): Presenter {
            return RegisterPresenter(view)
        }

        fun getModel(): Model {
            return RegisterModel()
        }
    }

}