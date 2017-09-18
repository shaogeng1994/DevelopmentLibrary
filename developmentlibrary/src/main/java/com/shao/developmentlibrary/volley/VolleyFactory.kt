package com.shao.developmentlibrary.volley

import android.content.Context

/**
 * Created by Administrator on 2017/9/13.
 */
open abstract class VolleyFactory {

    abstract fun getVolleyRequest(context: Context): VolleyRequest
}