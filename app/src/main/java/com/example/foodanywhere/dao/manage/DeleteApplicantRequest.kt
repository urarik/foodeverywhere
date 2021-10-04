package com.example.foodanywhere.dao.manage

import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest

private const val URL = "http://ouk49957.dothome.co.kr/deleteApplicant.php"

class DeleteApplicantRequest(userId: String, future: RequestFuture<String>): StringRequest(Method.POST, URL, future, future) {
    private val map: Map<String, String> = mapOf(
            "userId" to userId
    )

    override fun getParams(): Map<String, String> {
        return map
    }
}