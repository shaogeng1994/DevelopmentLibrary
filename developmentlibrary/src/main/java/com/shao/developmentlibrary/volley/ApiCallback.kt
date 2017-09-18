package com.shao.developmentlibrary.volley

import java.io.Serializable

/**
 * Created by Administrator on 2017/9/4.
 */
interface ApiCallback<T>: Serializable {
    fun callback(apiResponse: ApiResponse<T>)
}
