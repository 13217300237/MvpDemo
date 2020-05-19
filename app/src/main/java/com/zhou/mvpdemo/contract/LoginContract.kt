package com.zhou.mvpdemo.contract

import com.zhou.baselibrary.m.BaseModel
import com.zhou.baselibrary.network.HttpCallback
import com.zhou.baselibrary.p.BasePresenter
import com.zhou.baselibrary.v.BaseView
import com.zhou.mvpdemo.contract.m.LoginModel
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import com.zhou.mvpdemo.contract.p.LoginPresenter

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

    // 这里是不是可以提供静态方法，得到具体的P和M对象
    companion object {
        fun getPresenter(view: View): Presenter {
            return LoginPresenter(view)
        }

        fun getModel(): Model {
            return LoginModel()
        }
    }


}