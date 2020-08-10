package com.enplus.mvpdemo.v.activity

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.enplus.mvp.v.BaseActivity
import com.enplus.mvpdemo.R
import com.enplus.mvpdemo.contract.MainContract
import com.enplus.mvpdemo.contract.m.bean.articles.ArticleBean
import com.enplus.mvpdemo.contract.m.bean.banner.BannerBean
import com.enplus.mvpdemo.v.fragment.NoticeTipsFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * View层一个标准实现
 *
 * 继承BaseActivity，用泛型参数来指定P类，再实现一个接口来约束自身行为
 *
 * MainActivity: 一个app的主面板
 */
class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        btn1.setOnClickListener { getPresenter().getArticle() }

        btn2.setOnClickListener { getPresenter().getBanner() }

        btnToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnToLogin2.setOnClickListener {
            val intent = Intent(this, LoginActivity2::class.java)
            startActivity(intent)
        }

        addFragment()
    }

    private lateinit var fragment: Fragment
    private fun addFragment() {
        fragment = NoticeTipsFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContent, fragment, NoticeTipsFragment::class.java.simpleName)
            .show(fragment)
            .commitAllowingStateLoss()
    }

    override fun handlerArticles(bean: ArticleBean?) {
        dataView.text = bean.toString() //  我只能 打印他的toString, 但是如果我想
    }

    override fun handlerBanners(bean: BannerBean?) {
        dataView.text = bean.toString() //  我只能 打印他的toString, 但是如果我想
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

    override fun setPresenter(): MainContract.Presenter {
        return MainContract.getPresenter(this)
    }

}
