package com.shao.developmentlibrary.api

/**
 * Created by Administrator on 2017/9/9.
 */
/**
 * 登录请求
 * @param account 账号
 * @param password 密码
 */
class LoginReq(val account: String, val password: String): BaseRequest() {

    override fun getAction() = "login"

    override fun toMap(): HashMap<String, String> = createBaseMap().apply {
        put("account", account)
        put("password", password)
    }

}



