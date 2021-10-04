package com.example.foodanywhere.dao.apply

import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest

private const val URL = "http://ouk49957.dothome.co.kr/addApplicant.php"


class ApplicantAddRequest(val userId: String,
                     val description: String,
                     val image: String,
                     future: RequestFuture<String>
) : StringRequest(Method.POST, URL, future, future) {
    private val map: Map<String, String> = mapOf(
            "userId" to userId,
            "image" to image,
            "description" to description
    )

    override fun getParams(): Map<String, String> {
        return map
    }
}