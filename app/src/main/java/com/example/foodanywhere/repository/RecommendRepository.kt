package com.example.foodanywhere.repository

import android.content.Context
import android.util.LruCache
import com.example.foodanywhere.dao.recommned.RecommendDao
import com.example.foodanywhere.datatype.Cuisine


class RecommendRepository private constructor(context: Context, private val userCache: LruCache<String, Any>) {
    private val recommendDao = RecommendDao(context)

    fun getCuisineList(): List<Cuisine>? {

        val cache = userCache.get("List") as List<Cuisine>?
        if(cache != null) {
            return cache
        }
        val list = recommendDao.getCuisineList()
        list?.run{userCache.put("List", list)}
        return list
    }

    companion object {
        private var INSTANCE: RecommendRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null)
                INSTANCE = RecommendRepository(context, LruCache(30))
        }
        fun get(): RecommendRepository {
            return INSTANCE
                    ?:
                    throw IllegalAccessException("RecommendRepository must be initialized")
        }
    }

}
