package com.shao.developmentlibrary.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shao.developmentlibrary.api.BaseRequest
import org.jetbrains.anko.doAsync
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val result = URL("https://api.64clouds.com/v1/getServiceInfo?veid=635576&api_key=private_CSvHj2o2djTHUesVuysjK26V").readText()
            Log.i("shao", result)
        }
    }


    class LoginReq(val account: String, val password: String): BaseRequest() {

        override fun getAction() = "login"

        override fun toMap(): HashMap<String, String> = createBaseMap().apply {
            put("account", account)
            put("password", password)
        }

    }

}
