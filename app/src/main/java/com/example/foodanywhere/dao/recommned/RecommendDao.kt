package com.example.foodanywhere.dao.recommned

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.foodanywhere.ImageUtil
import com.example.foodanywhere.datatype.Cuisine
import org.json.JSONObject
import java.util.concurrent.TimeUnit

private const val URL ="http://ouk49957.dothome.co.kr/loadAllCuisines.php"

class RecommendDao(private val context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getCuisineList(): List<Cuisine>? {
        val future = RequestFuture.newFuture<String>()
        val cuisineRequest = StringRequest(URL, future, future)
        queue.add(cuisineRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("results")

            val cuisineList = mutableListOf<Cuisine>()
            for(i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val nation = item.getString("nation")
                val name = item.getString("name")
                val imgStr = item.getString("img")
                val bitmap = ImageUtil.StringToBitmap(imgStr)
                val description = item.getString("description")

                cuisineList.add(Cuisine(nation, name, description, bitmap))
            }
            cuisineList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}