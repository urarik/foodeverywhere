package com.example.foodanywhere.dao.manage

import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest

private const val URL = "http://ouk49957.dothome.co.kr/confirmCuisine.php"

class ConfirmCuisineRequest(name: String, future: RequestFuture<String>): StringRequest(Method.POST, URL, future, future) {
    private val map: Map<String, String> = mapOf(
            "name" to name
    )

    override fun getParams(): Map<String, String> {
        return map
    }
}