package com.reburn.module_user.p

import com.reburn.module_user.UserContract

/**
 *    author : ZhouHang
 *    e-mail : zhouhang@enfry.com
 *    date   : 2020/8/10_15:55
 *    desc   :
 *    version: 1.0
 */
class UserPresenter(v: UserContract.View) : UserContract.Presenter {

    //P 要持有 M和V的引用，只不过不用手动调用释放代码
    var model: UserContract.Model? = null
    var view: UserContract.View? = v

    override fun updateUserName() {
        view?.updateUserNameText(model?.getUserNameFromNet() ?: "")
    }

    override fun onCreate() {
        model = UserContract.getModel()
    }

    override fun onDestroy() {
        model = null
        view = null
    }
}