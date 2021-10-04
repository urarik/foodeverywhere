package com.example.foodanywhere.dao.review

import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest

private const val URL = "http://ouk49957.dothome.co.kr/loadReviews.php"
class ReviewLoadRequest(val nation: String, val name: String, future: RequestFuture<String>)
    : StringRequest(Method.POST, URL, future, future) {
    private val map: Map<String, String> = mapOf(
            "nation" to nation,
            "name" to name
    )

    override fun getParams(): Map<String, String> {
        return map
    }
}