package com.example.foodanywhere.dao.apply

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.datatype.Ingredient
import com.example.foodanywhere.datatype.Step
import com.example.foodanywhere.ImageUtil
import com.example.foodanywhere.datatype.Applicant
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

class ApplyDao(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun addUnConfirmedCuisine(cuisine: Cuisine): Boolean {
        val byteStream = ByteArrayOutputStream()
        val bitmap = cuisine.img ?: throw IllegalAccessException("No Image!")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteStream)
        val ary = byteStream.toByteArray()
        val imgStr = "\$image=" + ImageUtil.byteArrayToBinaryString(ary)

        var characteristicInt = 0
        val characteristicList = cuisine.characteristicList!!
        for (i in characteristicList.indices) {
            if (characteristicList[i])
                characteristicInt = characteristicInt or (1 shl i)
        }

        val future = RequestFuture.newFuture<String>()
        with(cuisine) {
            val cuisineAddRequest = CuisineAddRequest(nation, name, description, characteristicInt, imgStr, future)
            queue.add(cuisineAddRequest)
        }

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            jsonObject.getBoolean("success")
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    fun addIngredient(name: String, ingredientList: List<Ingredient>) {

        for (ingredient in ingredientList) {
            val ingredientName = ingredient.ingredientName
            val size = ingredient.quantity
            val ingredientRequest = IngredientAddRequest(name, ingredientName, size)
            queue.add(ingredientRequest)
        }

    }

    fun addStep(cuisineName: String, step: Step): Boolean {
        val byteStream = ByteArrayOutputStream()
        val bitmap = step.bitmap ?: throw IllegalAccessException("No Image!")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteStream)
        val ary = byteStream.toByteArray()

        val imgStr = "\$image=" + ImageUtil.byteArrayToBinaryString(ary)
        val future = RequestFuture.newFuture<String>()
        val stepAddRequest = StepAddRequest(cuisineName, step.description, imgStr, step.counter, future)
        queue.add(stepAddRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            jsonObject.getBoolean("success")
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun addApplicant(applicant: Applicant): Boolean {
        val byteStream = ByteArrayOutputStream()
        val bitmap = applicant.image ?: throw IllegalAccessException("No Image!")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteStream)
        val ary = byteStream.toByteArray()

        val imgStr = "\$image=" + ImageUtil.byteArrayToBinaryString(ary)
        val future = RequestFuture.newFuture<String>()
        Log.d("TAG", applicant.userId)
        val applicantAddRequest = ApplicantAddRequest(applicant.userId, applicant.description, imgStr, future)
        queue.add(applicantAddRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            jsonObject.getBoolean("success")
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}