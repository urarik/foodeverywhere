package com.example.foodanywhere.dao.review

import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.example.foodanywhere.datatype.Review
import com.example.foodanywhere.datatype.Step

private const val URL = "http://ouk49957.dothome.co.kr/addReview.php"
class AddReviewRequest(val nation: String, val name: String, val review: Review, future: RequestFuture<String>)
    : StringRequest(Method.POST, URL, future, future) {
    private val map: Map<String, String> = mapOf(
            "nation" to nation,
            "name" to name,
            "rating" to review.rating,
            "description" to review.description
    )

    override fun getParams(): Map<String, String> {
        return map
    }
}