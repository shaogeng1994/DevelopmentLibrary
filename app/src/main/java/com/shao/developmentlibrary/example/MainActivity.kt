package com.shao.developmentlibrary.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shao.developmentlibrary.api.BaseRequest
import org.jetbrains.anko.doAsync
import java.net.URL
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.image


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛")
        Glide.with(this).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640").into(player.thumbImageView)


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

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }

}
