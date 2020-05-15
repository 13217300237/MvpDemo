package com.zhou.mvpdemo.contract.p

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.mvpdemo.contract.RegisterContract
import com.zhou.mvpdemo.contract.m.RegisterModel
import com.zhou.mvpdemo.contract.m.bean.user.UserBean

class RegisterPresenter(view: RegisterContract.View) : RegisterContract.Presenter {

    // 为什么我要把 model 放在外面？一个业务类P，只会有一个model么？如果需要多个数据源呢？
    private var model: RegisterContract.Model? = null
    private var view: RegisterContract.View? = view

    override fun doRegister(
        username: String,
        pwd: String,
        rpwd: String
    ) {
        val v = view ?: return
        val m = model ?: return

        v.showLoading()
        m.doRegister(username, pwd, rpwd, object : HttpCallback<UserBean> {
            override fun onSuccess(result: UserBean?) {
                v.hideLoading()
                v.handlerRegisterResult(result)
            }

            override fun onFailure(e: Exception?) {
                v.hideLoading()
                v.onError(e.toString())
            }

        })
    }

    override fun onCreate() {
        model = RegisterContract.getModel()
    }

    override fun onDestroy() {
        model = null
        view = null
    }
}