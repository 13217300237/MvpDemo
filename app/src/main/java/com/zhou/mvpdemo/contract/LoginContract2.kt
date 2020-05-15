package com.zhou.mvpdemo.contract

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.mvpdemo.contract.m.LoginModel2
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import com.zhou.mvpdemo.contract.p.LoginPresenter2

/**
 * 由于M层和P层的大多数功能都和 之前的登录一毛一样！？那我还写个毛的代码，直接继承
 */
class LoginContract2 {
    interface Model : LoginContract.Model {
        fun doLogin2(
            username: String,
            password: String,
            userType: String,
            httpCallback: HttpCallback<UserBean>
        )
    }

    interface View : LoginContract.View {
        fun getUserType(): String
        fun handleLoginResultForSSSVIP(result: UserBean?)
        fun onErrorForSSSVIP(msg: String)
    }

    interface Presenter : LoginContract.Presenter {
        fun doLogin2(username: String, password: String, userType: String)
    }

    // 这里是不是可以提供静态方法，得到具体的P和M对象
    companion object {
        fun getPresenter(view: View): Presenter {
            return LoginPresenter2(view)
        }

        fun getModel(): Model {
            return LoginModel2()
        }
    }

}