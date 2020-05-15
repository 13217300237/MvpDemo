package com.zhou.mvpdemo.v.fragment

import android.util.Log
import android.view.View
import android.widget.TextView
import com.zhou.baselibrary.v.BaseFragment
import com.zhou.mvpdemo.R
import com.zhou.mvpdemo.contract.NoticeTipsContract

class NoticeTipsFragment : BaseFragment<NoticeTipsContract.Presenter>(), NoticeTipsContract.View {

    override fun getLayoutId(): Int {
        return R.layout.notice_fragment
    }

    private lateinit var tvMsg: TextView
    override fun init(root: View) {
        tvMsg = root.findViewById(R.id.tvMsg)
        castPresenter().updateMsg()
    }

    override fun castPresenter(): NoticeTipsContract.Presenter {
        return mPresenter as NoticeTipsContract.Presenter
    }

    override fun bindPresenter() {
        mPresenter = NoticeTipsContract.getPresenter(this)
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


}