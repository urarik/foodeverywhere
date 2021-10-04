package com.example.foodanywhere.repository

import android.content.Context
import android.util.LruCache
import com.example.foodanywhere.dao.cuisine.CuisineDao
import com.example.foodanywhere.dao.review.ReviewDao
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.datatype.Review
import com.example.foodanywhere.datatype.Step

class ReviewRepository private constructor(context: Context, private val userCache: LruCache<String, Any>){
    private val reviewDao = ReviewDao(context)

    fun getReviewList(nation: String, name: String): List<Review>? = reviewDao.getReviewList(nation, name)
    fun addReview(nation: String, name: String, review: Review): Boolean = reviewDao.addReview(nation, name, review)



    companion object {
        private var INSTANCE: ReviewRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null)
                INSTANCE = ReviewRepository(context, LruCache(30))
        }
        fun get(): ReviewRepository {
            return INSTANCE
                    ?:
                    throw IllegalAccessException("NationRepository must be initialized")
        }
    }
}