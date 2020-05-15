package com.zhou.mvpdemo.contract.p

import com.zhou.mvpdemo.contract.NoticeTipsContract

class NoticeTipsPresenter(view: NoticeTipsContract.View) : NoticeTipsContract.Presenter {

    // 为什么我要把 model 放在外面？一个业务类P，只会有一个model么？如果需要多个数据源呢？
    var model: NoticeTipsContract.Model? = null
    var view: NoticeTipsContract.View? = view

    override fun updateMsg() {
        val msg = model?.getMsg() ?: "空消息"
        view?.updateMsg(msg)
    }

    override fun onCreate() {
        model = NoticeTipsContract.getModel()
    }

    override fun onDestroy() {
        model = null
        view = null
    }
}