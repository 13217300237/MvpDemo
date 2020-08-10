package com.enplus.mvpdemo.contract.p

import com.enplus.network.HttpCallback
import com.enplus.mvpdemo.contract.RegisterContract
import com.enplus.mvpdemo.contract.m.bean.user.UserBean

class RegisterPresenter(view: RegisterContract.View) : RegisterContract.Presenter {

    // 为什么我要把 model 放在外面？一个业务类P，只会有一个model么？如果需要多个数据源呢？
    var model: RegisterContract.Model? = null
    var view: RegisterContract.View? = view
    override fun checkParams(): Boolean {
        val v = view ?: return false
        return v.getUsername().isNotBlank() && v.getPassword().isNotBlank() && v.getRePassword().isNotBlank()
    }

    override fun doRegister(
    ) {
        val v = view ?: return
        val m = model ?: return

        if (!checkParams()) {
            v.onError("有参数为空")
            return
        }

        v.showLoading()
        m.doRegister(
            v.getUsername(),
            v.getPassword(),
            v.getRePassword(),
            object : HttpCallback<UserBean> {
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