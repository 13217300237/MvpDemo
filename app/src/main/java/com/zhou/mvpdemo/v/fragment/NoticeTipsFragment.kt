package com.zhou.mvpdemo.v.fragment

import android.util.Log
import com.zhou.baselibrary.v.BaseFragment
import com.zhou.mvpdemo.R
import com.zhou.mvpdemo.contract.NoticeTipsContract
import kotlinx.android.synthetic.main.notice_fragment.*

class NoticeTipsFragment : BaseFragment<NoticeTipsContract.Presenter>(), NoticeTipsContract.View {

    override fun getLayoutId(): Int {
        return R.layout.notice_fragment
    }

    override fun init() {
        tvMsg.setOnClickListener {
            getPresenter().updateMsg()
        }
    }

    override fun updateMsg(msg: String) {
        Log.d("NoticeTipsFragment", "更新消息:$msg")
        tvMsg.text = msg
    }

    override fun showLoading() {
        // 这个用不着加载中效果，所以空即可
    }

    override fun hideLoading() {
        // 这个用不着加载中效果，所以空即可
    }

    override fun onError(msg: String) {
        tvMsg.text = msg
    }

    override fun onResume() {
        super.onResume()
        Log.d("NoticeTipsFragment", "onResume:${tvMsg.text}")
    }

    override fun setPresenter(): NoticeTipsContract.Presenter {
        return NoticeTipsContract.getPresenter(this)
    }


}