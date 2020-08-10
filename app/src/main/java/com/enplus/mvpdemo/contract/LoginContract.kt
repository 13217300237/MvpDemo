package com.enplus.mvpdemo.contract

import com.enplus.mvp.m.BaseModel
import com.enplus.network.HttpCallback
import com.enplus.mvp.p.BasePresenter
import com.enplus.mvp.v.BaseView
import com.enplus.mvpdemo.contract.m.LoginModel
import com.enplus.mvpdemo.contract.m.bean.user.UserBean
import com.enplus.mvpdemo.contract.p.LoginPresenter

class LoginContract {

    /**
     * 定义数据接口
     */
    interface Model : BaseModel {
        fun doLogin(username: String, password: String, httpCallback: HttpCallback<UserBean>)
    }

    /**
     * 定义View层的界面处理
     */
    interface View : BaseView {
        fun getUserName():String
        fun getPassword():String
        fun handleLoginResult(result: UserBean?)
    }

    /**
     * 定义P层的业务逻辑调用
     */
    interface Presenter : BasePresenter<BaseView> {
        fun checkParams(): Boolean
        fun doLogin()
    }

    companion object {
        fun getPresenter(view: View): Presenter {
            return LoginPresenter(view)
        }

        fun getModel(): Model {
            return LoginModel()
        }
    }


}