package com.shao.developmentlibrary.api

import java.io.Serializable
import java.util.HashMap

/**
 * Created by Administrator on 2017/9/9.
 */
abstract class BaseRequest : Serializable {


    var sysVersion = SYSVERSION
    var appVersion = APPVERSION
    var source = SOURCE
    var hash: String = getAction()


    fun createBaseMap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map.put("sysVersion", sysVersion)
        map.put("source", source)
        return map
    }


    abstract fun getAction(): String

    abstract fun toMap(): Map<String, String>

    companion object {
        /**
         * 设备  1.ios 2.android
         */
        val SYSVERSION = "2"
        /**
         * 接口版本
         */
        val APPVERSION = "1.0.7"
        /**
         * 来源
         */
        val SOURCE = "3"
    }

    fun <K, V> HashMap<K, V>.putNotNull(key: K, value: V?): Unit {
        if (value == null) return
        this.put(key, value)
    }
}
