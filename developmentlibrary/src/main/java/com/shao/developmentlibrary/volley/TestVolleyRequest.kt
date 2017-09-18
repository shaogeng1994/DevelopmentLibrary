package com.shao.developmentlibrary.volley

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.shao.developmentlibrary.api.BaseRequest
import java.lang.ref.WeakReference
import java.util.HashMap

/**
 * Created by Administrator on 2017/9/4.
 */
open class TestVolleyRequest(context: Context): VolleyRequest(context) {



    companion object {
        val URL: String = "http://192.168.0.28:8080/"

        @SuppressLint("StaticFieldLeak")
        private var instance: VolleyRequest? = null

        fun getInstance(context: Context): VolleyRequest {
            if (instance == null) {
                instance = TestVolleyRequest(context.applicationContext)
            }
            return instance as VolleyRequest
        }

        val mHandler = Handler(Looper.getMainLooper())

        private class ResponseListener<T>(private val clazz: Class<T>?, apiCallback: ApiCallback<T>) : Response.Listener<String> {
            private val apiCallback: WeakReference<ApiCallback<T>> = WeakReference(apiCallback)

            override fun onResponse(response: String) {
                if (apiCallback.get() != null) {
                    val runnable = ParseRunnable<T>(clazz, response, apiCallback.get()!!)
                    Thread(runnable).start()
                }
            }
        }

        private class MyErrorListener<T>(apiCallback: ApiCallback<T>) : Response.ErrorListener {

            private val apiCallback: WeakReference<ApiCallback<T>> = WeakReference(apiCallback)

            override fun onErrorResponse(volleyError: VolleyError) {
                Log.i("shao", "volleyError:" + volleyError.toString())
                if (apiCallback.get() == null) return
                val apiResponse = ApiResponse<T>()
                apiResponse.code = VOLLEY_ERROR_CODE
                if (volleyError.networkResponse != null) {
                    when (volleyError.networkResponse.statusCode) {
                        504 -> apiResponse.msg = "请求超时"
                        404 -> apiResponse.msg = "找不到服务器"
                        500 -> apiResponse.msg = "服务器未响应"
                        else -> apiResponse.msg = "请求失败"
                    }
                    apiCallback.get()?.callback(apiResponse)
                    return
                }
                apiResponse.msg = "请求异常"
                apiCallback.get()?.callback(apiResponse)
            }
        }

        private class ParseRunnable<T>(private val clazz: Class<T>?, private val response: String, apiCallback: ApiCallback<T>) : Runnable {
            private val apiCallback: WeakReference<ApiCallback<T>> = WeakReference(apiCallback)
            override fun run() {
                var apiResponse: ApiResponse<T>? = null
                try {
                    val sys = JSON.parseObject(response)
                    if ("1" == sys.getString("code")) {
                        val root = sys.getJSONObject("data")
                        if (root["info"] is JSONObject || root["data"] is JSONObject) {
                            val info = root.getJSONObject("info")
                            val data = root.getJSONObject("data")
                            apiResponse = JSON.parseObject<ApiResponse<T>>(root.toJSONString(), ApiResponse::class.java)
                            if (info != null && clazz != null)
                                apiResponse!!.info = JSON.parseObject<T>(info.toString(), clazz)
                            if (data != null && clazz != null)
                                apiResponse!!.data = JSON.parseObject<T>(data.toString(), clazz)
                        } else if (root["info"] is JSONArray || root["data"] is JSONArray) {
                            val info = root.getJSONArray("info")
                            val data = root.getJSONArray("data")
                            var apiResponseList: ApiResponse<List<T>> = JSON.parseObject<ApiResponse<List<T>>>(root.toJSONString(), ApiResponse::class.java)
                            if (info != null && clazz != null)
                                apiResponseList.info = JSON.parseArray<T>(info.toString(), clazz)
                            if (data != null && clazz != null)
                                apiResponseList.data = JSON.parseArray<T>(data.toString(), clazz)
                        } else {
                            apiResponse = JSON.parseObject<ApiResponse<T>>(root.toJSONString(), ApiResponse::class.java)
                        }
                        apiResponse!!.original = root.toJSONString()
                    } else if ("-996" == sys.getString("code")) {
                        apiResponse = ApiResponse()
                        apiResponse.code = "101"
                        apiResponse.msg = "token无效"
                    } else {
                        apiResponse = JSON.parseObject<ApiResponse<T>>(sys.toJSONString(), ApiResponse::class.java)
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                    apiResponse = ApiResponse()
                    apiResponse.code = JSON_ERROR_CODE
                    apiResponse.msg = JSON_ERROR_MSG
                } finally {
                    mHandler.post {
                        apiCallback.get()?.callback(apiResponse!!)
                    }
                }
            }
        }
    }

    override fun <T> doRequest(req: BaseRequest, clazz: Class<T>?, apiCallback: ApiCallback<T>, tag: String?) {
        if (!NetworkUtil.isNetworkConnected(context)) {
            val apiResponse = ApiResponse<T>()
            apiResponse.code = NO_NETWORK_ERROR_CODE
            apiResponse.msg = NO_NETWORK_ERROR_MSG
            apiCallback.callback(apiResponse)
            return
        }


        val listener = ResponseListener<T>(clazz, apiCallback)
        val errorListener = MyErrorListener(apiCallback)


        val stringRequest = object : StringRequest(Request.Method.POST, URL + req.hash,
                listener, errorListener) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                return req.toMap()
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val map = HashMap<String, String>()
                map.put("VERSION", req.appVersion)
                return map
            }
        }

        stringRequest.tag = tag?:req.hash
        addToRequest(stringRequest)
    }




}