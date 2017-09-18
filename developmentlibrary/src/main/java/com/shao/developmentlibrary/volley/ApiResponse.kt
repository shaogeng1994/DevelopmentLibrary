package com.shao.developmentlibrary.volley

import android.text.TextUtils
import java.io.Serializable

/**
 * Created by Administrator on 2017/9/4.
 */
data class ApiResponse<T> constructor(
        var code: String? = null,
        var msg: String? = null,
        var data: T? = null,
        var info: T? = null,
        var ret: String? = null,
        var original: String? = null): Serializable {

    constructor(): this(null, null, null, null, null, null)

    fun isSuccess(): Boolean {
        if (TextUtils.isEmpty(ret)) {
            if (code != null) {
                return "1" == code || "200" == code
            } else {
                return false
            }
        } else {
            return ret == "200"
        }
    }

    fun isTokenError(): Boolean {
        if (code != null) {
            if (code == "101" || code == "102" || code == "103") return true
        }
        return false
    }
}