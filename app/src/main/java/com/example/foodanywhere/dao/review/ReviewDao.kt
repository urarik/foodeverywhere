package com.example.foodanywhere.dao.review

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import com.example.foodanywhere.datatype.Review
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ReviewDao(private val context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getReviewList(nation: String, name: String): List<Review>? {
        val future = RequestFuture.newFuture<String>()
        val reviewLoadRequest = ReviewLoadRequest(nation, name, future)
        queue.add(reviewLoadRequest)

        return try {
            val response = future.get(10, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("results")

            val reviewList = mutableListOf<Review>()
            for (i in 0 until jsonArray.length()) {
                val rating = jsonArray.getJSONObject(i).getString("rating")
                val description = jsonArray.getJSONObject(i).getString("description")
                reviewList.add(Review(rating, description))
            }

            reviewList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun addReview(nation: String, name: String, review: Review): Boolean {
        val future = RequestFuture.newFuture<String>()
        val addReviewRequest = AddReviewRequest(nation, name, review, future)
        queue.add(addReviewRequest)

        try {
            val response = future.get(10, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            return jsonObject.getBoolean("success")
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalAccessException()
        }
    }
}