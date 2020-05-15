package com.zhou.mvpdemo.v.activity

import android.util.Log
import android.view.View
import com.zhou.baselibrary.v.BaseActivity
import com.zhou.mvpdemo.R
import com.zhou.mvpdemo.contract.LoginContract
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 完成V类，主要写的是调用P类的代码，以及自身的UI交互,
 * 不要去写任何的具体的业务流程，比如，登录过程中，从界面上拿到用户名和密码，然后传给P的login方法即可，其他的一概不要做。
 * 不要在这里写登录之后的回调。
 *
 * 比如在注册界面，要校验密码规则，防止用户密码过于简单。
 * 或者校验用户名，用户名必须包含字母和数字，位数不少于6个，必须以字母开头，能否使用中文等等。
 *
 * 又比如，主界面上，现在是有两块区域显示新闻和banner，你要求app在某个时段只显示新闻，你要求app只抓取今天的新闻，
 * 这时候就要传入日期参数给Model层，日期的获取只能在P层，你不能在V层获取之后，传给P。
 *
 * MVP铁则！
 *
 * M模型层（或者数据层）。
 * V 视图层
 * P M和V的粘合剂（Presenter本身就是粘合剂的意思）
 *
 * 从上往下：
 * V层是皮囊，只负责调动UI，以及 触发P层接口。理论上，不依赖P层也能独立演示UI效果。
 * P层是业务层，只负责写业务逻辑，比如 处理V层传递的数据，判断不合格就反馈给V层告诉他数据不正确，不会用这个数据去调用M层
 * M层是纯粹的数据层，就算脱离了V和P都可以单独测试。
 */
open class LoginActivity : BaseActivity<LoginContract.Presenter>(), LoginContract.View {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun init() {
        btnLogin.setOnClickListener {
            if (checkParams()) {
                val username = tvUsername.text.trim().toString()
                val password = tvPassword.text.trim().toString()
                castPresenter().doLogin(username, password)
            } else {
                onError("有参数为空..")
            }

        }
    }

    /**
     * 这个可以算是特约P类,其实我还可以针对通用P类在这里独立创建一个P对象。
     */
    override fun castPresenter(): LoginContract.Presenter {
        return mPresenter as LoginContract.Presenter
    }

    override fun bindPresenter() {
        mPresenter = LoginContract.getPresenter(this)
        // 这种情况，到底是属于谁持有了谁的引用,双方都是对方的成员属性。这么看来，应该是相互持有。
    }

    override fun checkParams(): Boolean {
        return tvUsername.text.isNotEmpty() && tvPassword.text.isNotEmpty()
    }

    override fun handleLoginResult(result: UserBean?) {
        Log.d("handleLoginResult", result.toString())
        dataView.text = result.toString()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onError(msg: String) {
        dataView.text = msg
    }
}
