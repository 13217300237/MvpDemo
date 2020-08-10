package com.reburn.modeling.p

import com.reburn.modeling.BusinessModelingContract

/**
 *    author : ZhouHang
 *    e-mail : zhouhang@enfry.com
 *    date   : 2020/8/10_15:55
 *    desc   :
 *    version: 1.0
 */
class BusinessModelingPresenter(v: BusinessModelingContract.View) : BusinessModelingContract.Presenter {

    //P 要持有 M和V的引用，只不过不用手动调用释放代码
    var model: BusinessModelingContract.Model? = null
    var view: BusinessModelingContract.View? = v

    override fun updateMsg() {
        view?.updateMsgText(model?.getMsgFromNet() ?: "")
    }

    override fun onCreate() {
        model = BusinessModelingContract.getModel()
    }

    override fun onDestroy() {
        model = null
        view = null
    }
}