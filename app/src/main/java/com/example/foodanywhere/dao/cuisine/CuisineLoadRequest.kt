package com.example.foodanywhere.dao.cuisine

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

private const val URL = "http://ouk49957.dothome.co.kr/loadimg.php"

class CuisineLoadRequest(val name: String, listener: Response.Listener<String>)
    : StringRequest(Method.POST, URL, listener, null) {
    private val map: Map<String, String> = mapOf(
        "name" to name
    )

    override fun getParams(): Map<String, String> {
        return map
    }
}