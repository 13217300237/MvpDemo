package com.enplus.utils

import android.text.TextUtils

import java.util.regex.Pattern

/**
 * Description: <ToastUtil><br>
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
</ToastUtil> */
object InfoVerify {
    /**
     * 校验邮箱
     *
     * @param paramString
     * @return
     */
    fun isValidEmail(paramString: String): Boolean {
        val regex = "[a-zA-Z0-9_\\.]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}"
        return paramString.matches(regex.toRegex())
    }

    /**
     * 校验ＱＱ
     *
     * @param paramString
     * @return
     */
    fun isValidQQ(paramString: String): Boolean {
        val regex = "^[1-9](\\d){4,9}$"
        return paramString.matches(regex.toRegex())
    }

    /**
     * 校验车牌号
     *
     * @param paramString
     * @return
     */
    fun isValidPlatnum(paramString: String): Boolean {
        if (TextUtils.isEmpty(paramString)) return false
        val regex = "^[\u4e00-\u9fa5]{1}[A-Z_a-z]{1}[A-Z_0-9_a-z]{5}$"
        return paramString.matches(regex.toRegex())
    }

    /**
     * 校验手机号
     *
     * @param paramString
     * @return
     */
    fun isValidMobiNumber(paramString: String?): Boolean {
        // String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        if (paramString == null) return false
        val regex = "^1\\d{10}$"
        return paramString.matches(regex.toRegex())
    }

    fun isNumeric(str: String): Boolean {
        val pattern = Pattern.compile("^[0-9]+\\.?[0-9]*[0-9]$")
        val isNum = pattern.matcher(str)
        return isNum.matches()
    }
}