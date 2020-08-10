package com.enplus.mvpdemo.contract.m

import com.enplus.mvpdemo.contract.NoticeTipsContract

class NoticeTipsModel : NoticeTipsContract.Model {


    override fun getMsg(): String {
        // 假装你正在从数据库中读取最新一条消息
        return "假装从数据库中读取最新一条消息"// 先写死
    }
}