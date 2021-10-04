package com.example.foodanywhere.dao.cuisine

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.datatype.Ingredient
import com.example.foodanywhere.datatype.Step
import com.example.foodanywhere.ImageUtil
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.Comparator
import kotlin.collections.ArrayList

class CuisineDao(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getCuisineList(nation: String): List<String>? {
        val future = RequestFuture.newFuture<String>()
        val cuisineListRequest = CuisineListLoadRequest(nation, future)
        queue.add(cuisineListRequest)
        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("results")

            val cuisineList = mutableListOf<String>()
            for (i in 0 until jsonArray.length()) {
                val name = jsonArray.getJSONObject(i).getString("name")
                cuisineList.add(name)
            }
            cuisineList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getCuisine(name: String)
            : Cuisine? {

        val future = RequestFuture.newFuture<String>()
        val cuisineRequest = CuisineRequest(name, future)
        queue.add(cuisineRequest)
        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            val nation = jsonObject.getString("nation")
            val description = jsonObject.getString("description")
            val imgStr = jsonObject.getString("img")
            val bitmap = ImageUtil.StringToBitmap(imgStr)

            val characteristic = jsonObject.getString("characteristic").toInt()
            val characteristicList = ArrayList<Boolean>()
            for (i in 0..9) {
                characteristicList.add(((characteristic shr i) and 1) == 1)
            }

            Cuisine(nation, name, description, bitmap, characteristicList)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    @SuppressLint("SetTextI18n")
    fun getIngredient(name: String): List<Ingredient>? {
        val future = RequestFuture.newFuture<String>()
        val ingredientRequest = IngredientRequest(name, future)
        queue.add(ingredientRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("results")

            val ingredientList = mutableListOf<Ingredient>()
            for (i in 0 until jsonArray.length()) {
                val ingredientName = jsonArray.getJSONObject(i).getString("ingre")
                val quantity = jsonArray.getJSONObject(i).getString("size")
                ingredientList.add(Ingredient(ingredientName, quantity))
            }

            ingredientList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getStepList(name: String): List<Step>? {
        val comparator = Comparator<Step> { step1, step2 ->
            when {
                step1.counter == -1 -> 1
                step2.counter == -1 -> -1
                else -> when {
                    step1.counter > step2.counter -> 1
                    step1.counter < step2.counter -> -1
                    else -> 0
                }
            }
        }
        val future = RequestFuture.newFuture<String>()
        val stepRequest = StepRequest(name, future)
        queue.add(stepRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("results")

            val stepList = mutableListOf<Step>()
            for (i in 0 until jsonArray.length()) {
                val image = jsonArray.getJSONObject(i).getString("image")
                val description = jsonArray.getJSONObject(i).getString("description")
                val counter = jsonArray.getJSONObject(i).getInt("counter")
                val bitmap = ImageUtil.StringToBitmap(image)
                stepList.add(Step(bitmap!!, description, counter))
            }
            Collections.sort(stepList, comparator)

            stepList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}