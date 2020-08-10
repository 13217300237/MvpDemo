package com.enplus.view.refresh

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi

/**
 * Description: <小菊花样式的刷新控件><br>
 * Author:      mxdl<br>
 * Date:        2019/2/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class DaisyRefreshLayout(context: Context, attrs: AttributeSet) :
    BaseRefreshLayout(context, attrs) {
    private val mDaisyHeaderView: DaisyHeaderView = DaisyHeaderView(context)
    private val mDaisyFooterView: DaisyFooterView = DaisyFooterView(context)
    override var isRefreshing: Boolean
        get() = super.isRefreshing
        set(refreshing) {
            mDaisyHeaderView.setRefreshing(refreshing)
            super.isRefreshing = refreshing
        }

    init {
        setHeaderView(mDaisyHeaderView)
        setFooterView(mDaisyFooterView)
        setOnPullRefreshListener(object : OnPullRefreshListener {
            override fun onRefresh() {
                mDaisyHeaderView.onRefresh()
                mOnRefreshListener?.onRefresh()
            }

            override fun onPullDistance(distance: Int) {

            }

            override fun onPullEnable(enable: Boolean) {
                mDaisyHeaderView.onPullEnable(enable)
            }
        })
        setOnPushLoadMoreListener(object : OnPushLoadMoreListener {
            override fun onLoadMore() {
                mDaisyFooterView.onLoadMore()
                mOnLoadMoreListener?.onLoadMore()
            }

            override fun onPushDistance(distance: Int) {

            }

            override fun onPushEnable(enable: Boolean) {
                mDaisyFooterView.onPushEnable(enable)
            }
        })
    }

    override fun showRefresh() {
        mDaisyHeaderView.onRefresh()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun setLoadMore(loadMore: Boolean) {
        mDaisyFooterView.setLoadMore(loadMore)
        super.setLoadMore(loadMore)
    }
}
