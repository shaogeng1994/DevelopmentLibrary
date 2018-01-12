package com.shao.developmentlibrary.example.elecrecord

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shao.developmentlibrary.example.BaseActivity
import com.shao.developmentlibrary.volley.TestVolleyFactory

/**
 * Created by Administrator on 2017/10/12.
 */
class ElectricityListActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        volleyRequest = TestVolleyFactory().getVolleyRequest(applicationContext)
    }



}