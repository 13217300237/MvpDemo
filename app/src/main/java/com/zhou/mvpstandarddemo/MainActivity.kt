package com.zhou.mvpstandarddemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zhou.mvpstandarddemo.p.MainPagePresenter
import com.zhou.mvpstandarddemo.v.base.BaseView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * View层一个标准实现
 */
class MainActivity : AppCompatActivity(), BaseView {

    private val newsPresenter = MainPagePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        lifecycle.addObserver(newsPresenter) // 利用 lifecycle 防止内存泄漏
        btn1.setOnClickListener {
            val data = newsPresenter.getArticle()
            if (data != null) {
                dataView.text = data.toString()
            } else {
                onError()
            }
        }

        btn2.setOnClickListener {
            val data = newsPresenter.getBanner()
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
