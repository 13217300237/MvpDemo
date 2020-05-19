package com.zhou.mvpdemo.contract.p

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.mvpdemo.contract.LoginContract
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import kotlinx.android.synthetic.main.activity_login.*

open class LoginPresenter(view: LoginContract.View) : LoginContract.Presenter {

    //P类，持有M和V的引用
    // 为什么我要把 model 放在外面？一个业务类P，只会有一个model么？如果需要多个数据源呢？
    var model: LoginContract.Model? = null
    var view: LoginContract.View? = view


    override fun checkParams(): Boolean {
        val v = view ?: return false
        return v.getUserName().isNotBlank() && v.getPassword().isNotBlank()
    }

    override fun doLogin() {
        val v = view ?: return
        val m = model ?: return

        if (!checkParams()) {
            v.onError("有参数为空...")
            return
        }

        v.showLoading()
        m.doLogin(v.getUserName(), v.getPassword(), object : HttpCallback<UserBean> {
            override fun onSuccess(result: UserBean?) {
                v.hideLoading()
                v.handleLoginResult(result)
            }

            override fun onFailure(e: Exception?) {
                v.hideLoading()
                v.onError(e.toString())
            }

        })
    }

    override fun onCreate() {
        model = LoginContract.getModel()
    }

    override fun onDestroy() {
        model = null
        view = null
    }
}