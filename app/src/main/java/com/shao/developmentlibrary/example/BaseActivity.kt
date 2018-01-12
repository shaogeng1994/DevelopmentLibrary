package com.shao.developmentlibrary.example

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import com.shao.developmentlibrary.volley.VolleyRequest

/**
 * Created by Administrator on 2017/10/12.
 */
open class BaseActivity: AppCompatActivity() {

    var loadingDialog: Dialog? = null
    lateinit var volleyRequest : VolleyRequest

    fun setLoading(isShow: Boolean) {
        if (isShow) {
            loadingDialog = Dialog(this).apply {
                setContentView(ProgressBar(applicationContext))
                setOnCancelListener {
                    volleyRequest.removeRequest(this@BaseActivity.javaClass.simpleName)
                }
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }

    }
}