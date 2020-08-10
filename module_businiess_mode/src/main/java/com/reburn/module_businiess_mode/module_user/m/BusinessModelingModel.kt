package com.reburn.module_businiess_mode.module_user.m

import com.reburn.module_user.BusinessModelingContract

/**
 *    author : ZhouHang
 *    e-mail : zhouhang@enfry.com
 *    date   : 2020/8/10_15:54
 *    desc   :
 *    version: 1.0
 */
class BusinessModelingModel : BusinessModelingContract.Model {
    override fun getMsgFromNet(): String {
        return "假装从网络服务器上获取到了username"
    }
}