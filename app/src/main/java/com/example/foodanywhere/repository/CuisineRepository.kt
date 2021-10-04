package com.example.foodanywhere.repository

import com.example.foodanywhere.dao.cuisine.CuisineDao
import android.content.Context
import android.util.LruCache
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.datatype.Step

class CuisineRepository private constructor(context: Context, private val userCache: LruCache<String, Any>){
    private val cuisineDao = CuisineDao(context)

    fun getCuisineList(nation: String): List<String>? = cuisineDao.getCuisineList(nation)

    fun getCuisine(name: String):Cuisine? {
        val cache = userCache.get(name + "C") as Cuisine?
        if(cache != null) {
            return cache
        }
        val cuisine = cuisineDao.getCuisine(name)?.apply {
            ingredientList = cuisineDao.getIngredient(name)
            userCache.put(name + "C", this)
        }
        return cuisine
    }

    fun getStepList(name: String): List<Step>? {
        val cache = userCache.get(name + "S") as List<Step>?
        if(cache != null) {
            return cache
        }
        val step = cuisineDao.getStepList(name)
        userCache.put(name + "I", step)
        return step
    }

    companion object {
        private var INSTANCE: CuisineRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null)
                INSTANCE = CuisineRepository(context, LruCache(30))
        }
        fun get(): CuisineRepository {
            return INSTANCE
                    ?:
                    throw IllegalAccessException("NationRepository must be initialized")
        }
    }
}