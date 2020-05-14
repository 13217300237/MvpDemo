package com.zhou.mvpstandarddemo

import android.view.View
import com.zhou.mvpstandarddemo.p.MainPagePresenter
import com.zhou.mvpstandarddemo.v.base.BaseActivity
import com.zhou.mvpstandarddemo.v.base.BaseView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * View层一个标准实现
 */
class MainActivity : BaseActivity<MainPagePresenter>(), BaseView {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun bindPresenter() {
        mPresenter = MainPagePresenter(this)
    }

    override fun castPresenter(): MainPagePresenter {
        return mPresenter as MainPagePresenter
    }

    override fun init() {
        btn1.setOnClickListener {
            val data = castPresenter().getArticle()
            if (data != null) {
                dataView.text = data.toString()
            } else {
                onError()
            }
        }

        btn2.setOnClickListener {
            val data = castPresenter().getBanner()
            if (data != null) {
                dataView.text = data.toString()
            } else {
                onError()
            }
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onError() {
        dataView.text = "获取内容失败!"
    }

    override fun <T> onSuccess(t: T) {
        //  据说可以根据泛型对象来判断真实类型？
        dataView.text = t.toString()
    }


}
