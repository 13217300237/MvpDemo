package com.reburn.user.m

import com.reburn.user.UserContract

/**
 *    author : ZhouHang
 *    e-mail : zhouhang@enfry.com
 *    date   : 2020/8/10_15:54
 *    desc   :
 *    version: 1.0
 */
class UserModel : UserContract.Model {
    override fun getUserNameFromNet(): String {
        return "假装从网络服务器上获取到了username"
    }
}