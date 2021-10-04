package com.example.foodanywhere.dao.cuisine

import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest

private const val URL = "http://ouk49957.dothome.co.kr/loadCuisines.php"
class CuisineListLoadRequest(val nation: String, future: RequestFuture<String>)
    : StringRequest(Method.POST, URL, future, future) {
    private val map: Map<String, String> = mapOf(
            "nation" to nation
    )

    override fun getParams(): Map<String, String> {
        return map
    }
}