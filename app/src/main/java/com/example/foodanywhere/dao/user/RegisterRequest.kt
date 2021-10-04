package com.example.foodanywhere.dao.user

import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest

private const val URL = "http://ouk49957.dothome.co.kr/register.php"

class RegisterRequest(iD: String, password: String, email: String, future: RequestFuture<String>)
    : StringRequest(Method.POST,
    URL, future, future) {
    private val map: Map<String, String> = mapOf(
            "userID" to iD,
            "userPassword" to password,
            "userEmail" to email,
            "userState" to "1"
    )

    override fun getParams(): Map<String, String> {
        return map
    }


}