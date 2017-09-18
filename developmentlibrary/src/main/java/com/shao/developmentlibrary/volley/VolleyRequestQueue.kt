package com.shao.developmentlibrary.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Created by Administrator on 2017/9/4.
 */

class VolleyRequestQueue private constructor(context: Context) {
    var requestQueue: RequestQueue? = null
        private set

    init {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.applicationContext)
        }
    }

    companion object {
        private var instance: VolleyRequestQueue? = null
        fun getInstance(context: Context): VolleyRequestQueue {
            if (instance == null) instance = VolleyRequestQueue(context.applicationContext)
            return instance as VolleyRequestQueue
        }
    }
}