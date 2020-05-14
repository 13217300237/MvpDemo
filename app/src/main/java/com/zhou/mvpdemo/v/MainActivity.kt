package com.zhou.mvpdemo.v

import android.view.View
import com.zhou.mvpdemo.p.MainPagePresenter
import com.zhou.baselibrary.v.BaseActivity
import com.zhou.baselibrary.v.BaseView
import com.zhou.mvpdemo.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * View层一个标准实现
 */
class MainActivity : BaseActivity<MainPagePresenter>() {

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

    /**
     * 我拿不到具体的类型，应该就不能对它进行处理
     */
    override fun <T> onSuccess(t: T) {
        //  据说可以根据泛型对象来判断真实类型？
        dataView.text = t.toString() //  我只能 打印他的toString, 但是如果我想
    }


}
