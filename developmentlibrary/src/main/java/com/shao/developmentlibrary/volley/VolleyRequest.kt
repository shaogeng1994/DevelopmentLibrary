package com.shao.developmentlibrary.volley

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.shao.developmentlibrary.api.BaseRequest

/**
 * Created by Administrator on 2017/9/13.
 */
open abstract class VolleyRequest(context: Context) {


    companion object {
        val JSON_ERROR_MSG = "服务器繁忙，稍后再试"
        val JSON_ERROR_CODE = "100"
        val NO_NETWORK_ERROR_MSG = "当前无可用网络"
        val NO_NETWORK_ERROR_CODE = "99"
        val VOLLEY_ERROR_CODE = "98"
    }

    protected var mRequestQueue: RequestQueue = VolleyRequestQueue.getInstance(context).requestQueue!!
    protected var context: Context = context.applicationContext


    abstract fun <T> doRequest(req: BaseRequest, clazz: Class<T>?, apiCallback: ApiCallback<T>, tag: String? = null)

    fun addToRequest(request: Request<*>) {
        request.retryPolicy = DefaultRetryPolicy(30 * 1000, 1, 1.0f)
        mRequestQueue.add(request)
    }

    fun removeAllRequest() {
        mRequestQueue.cancelAll {
            true
        }
    }

    fun removeRequest(tag: String) {
        mRequestQueue.cancelAll({request ->  request.tag == tag})
    }
}