package com.example.foodanywhere.dao.apply

import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest

private const val URL = "http://ouk49957.dothome.co.kr/addSteps.php"


class StepAddRequest(val cuisine: String,
                     val description: String,
                     val image: String,
                     val counter: Int,
                     future: RequestFuture<String>
) : StringRequest(Method.POST, URL, future, future) {
    private val map: Map<String, String> = mapOf(
            "cuisine" to cuisine,
            "description" to description,
            "image" to image,
            "counter" to counter.toString()
    )

    override fun getParams(): Map<String, String> {
        return map
    }
}