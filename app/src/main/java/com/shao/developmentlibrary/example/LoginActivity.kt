package com.shao.developmentlibrary.example

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.shao.developmentlibrary.api.BaseRequest
import com.shao.developmentlibrary.api.LoginReq
import com.shao.developmentlibrary.api.User
import com.shao.developmentlibrary.volley.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Administrator on 2017/8/30.
 */
open class LoginActivity: AppCompatActivity() {

    var loadingDialog: Dialog? = null
    val volleyFactory = TestVolleyFactory().getVolleyRequest(applicationContext)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_login.setOnClickListener({
            createDialog("是否登录？", { _, _ -> login()})
        })

    }


    fun login() {
        val account = login_account.text.toString()

        val password = login_password.text.toString()

        if (TextUtils.isEmpty(account)) {
            showToast("请输入账号")
            return
        }

        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码")
            return
        }

        setLoading(true)
        LoginReq(account, password).let {
            volleyFactory.doRequest(it, User::class.java, object : ApiCallback<User>{
                override fun callback(apiResponse: ApiResponse<User>) {
                    setLoading(false)
                    if (apiResponse.isSuccess()) {
                        showToast("登录成功")
                        showToast("欢迎${apiResponse.data?.nickName?:""}!")
                    } else {
                        showToast(apiResponse.msg)
                    }

                }
            }, this.javaClass.simpleName)

        }

    }

    fun Context.showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun Context.createDialog(msg: String?, listener: (DialogInterface, Int) -> Unit, title: String = "提示") {
        AlertDialog.Builder(this).setTitle(title)
                .setMessage(msg)
                .setNegativeButton("取消", { _, _ ->  })
                .setPositiveButton("确认", listener)
                .show()
    }

    fun setLoading(isShow: Boolean) {
        if (isShow) {
            loadingDialog = Dialog(this).apply {
                setContentView(ProgressBar(applicationContext))
                setOnCancelListener {
                    volleyFactory.removeRequest(this@LoginActivity.javaClass.simpleName)
                }
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }

    }

//    override fun onBackPressed() {
//        Log.i("shao", "back")
//        if (loadingDialog != null) {
//            Log.i("shao", "cancel")
//            VolleyRequest.getInstance(this).removeRequest(this.javaClass.simpleName)
//        }
//        super.onBackPressed()
//    }

}
