package com.shao.developmentlibrary.volley

import android.content.Context

/**
 * Created by Administrator on 2017/9/13.
 */
open class TestVolleyFactory: VolleyFactory() {

    override fun getVolleyRequest(context: Context): VolleyRequest = TestVolleyRequest.getInstance(context)
}