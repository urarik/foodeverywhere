package com.example.foodanywhere.repository

import android.content.Context
import android.util.LruCache
import com.example.foodanywhere.dao.apply.ApplyDao
import com.example.foodanywhere.datatype.Applicant
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.datatype.Ingredient
import com.example.foodanywhere.datatype.Step

class ApplyRepository private constructor(context: Context, private val userCache: LruCache<String, Any>){
    private val applyDao = ApplyDao(context)

    fun addUnConfirmedCuisine(cuisine: Cuisine) : Boolean = applyDao.addUnConfirmedCuisine(cuisine)
    fun addIngredient(name: String, ingredientList: List<Ingredient>) = applyDao.addIngredient(name, ingredientList)
    fun addStep(cuisineName: String, step: Step): Boolean = applyDao.addStep(cuisineName, step)
    fun addApplicant(applicant: Applicant): Boolean = applyDao.addApplicant(applicant)

    companion object {
        private var INSTANCE: ApplyRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null)
                INSTANCE = ApplyRepository(context, LruCache(30))
        }
        fun get(): ApplyRepository {
            return INSTANCE
                    ?:
                    throw IllegalAccessException("NationRepository must be initialized")
        }
    }
}