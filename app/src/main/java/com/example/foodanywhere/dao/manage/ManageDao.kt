package com.example.foodanywhere.dao.manage

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.foodanywhere.ImageUtil
import com.example.foodanywhere.R
import com.example.foodanywhere.dao.cuisine.CuisineRequest
import com.example.foodanywhere.datatype.Applicant
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.view.manage.ManageCuisineFragment
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

private const val URL ="http://ouk49957.dothome.co.kr/loadUncomfirmedCuisines.php"
private const val URL2 ="http://ouk49957.dothome.co.kr/loadApplicants.php"

class ManageDao(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getUnConfirmedCuisineList(): List<Cuisine>? {
        val future = RequestFuture.newFuture<String>()
        val cuisineRequest = StringRequest(URL, future, future)
        queue.add(cuisineRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("results")

            val unConfirmedCuisineList = mutableListOf<Cuisine>()
            for(i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val nation = item.getString("nation")
                val name = item.getString("name")
                unConfirmedCuisineList.add(Cuisine(nation, name))
            }
            unConfirmedCuisineList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getApplicantList():List<String>? {
        val future = RequestFuture.newFuture<String>()
        val cuisineRequest = StringRequest(URL2, future, future)
        queue.add(cuisineRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("results")

            val applicantList = mutableListOf<String>()
            for(i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val userId = item.getString("userId")
                applicantList.add(userId)
            }
            applicantList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getApplicant(userId: String): Applicant? {
        val future = RequestFuture.newFuture<String>()
        val applicantRequest = ApplicantRequest(userId, future)
        queue.add(applicantRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            val description = jsonObject.getString("description")
            val image = jsonObject.getString("image")
            val bitmap = ImageUtil.StringToBitmap(image)
            val applicant = Applicant(userId, bitmap, description)
            Applicant(userId, bitmap, description)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun confirmCuisine(name: String): Boolean{
        val future = RequestFuture.newFuture<String>()
        val confirmRequest = ConfirmCuisineRequest(name, future)
        queue.add(confirmRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            jsonObject.getBoolean("success")
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    fun deleteCuisine(name: String): Boolean{
        val future = RequestFuture.newFuture<String>()
        val deleteRequest = DeleteCuisineRequest(name, future)
        queue.add(deleteRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            jsonObject.getBoolean("success")
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun confirmApplicant(userId: String): Boolean {
        val future = RequestFuture.newFuture<String>()
        val confirmRequest = ConfirmApplicantRequest(userId, future)
        queue.add(confirmRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            jsonObject.getBoolean("success")
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    fun deleteApplicant(userId: String): Boolean {
        val future = RequestFuture.newFuture<String>()
        val deleteRequest = DeleteApplicantRequest(userId, future)
        queue.add(deleteRequest)

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