package com.example.foodanywhere.dao.user

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import com.example.foodanywhere.datatype.UserInfo
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class UserDao(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getUserState(userID: String, userPassword: String): Int {
        val future = RequestFuture.newFuture<String>()
        val loginRequest = LoginRequest(userID, userPassword, future)
        queue.add(loginRequest)

        return try {
            val response = future.get(1, TimeUnit.SECONDS)
            val jsonObject = JSONObject(response)
            val success = jsonObject.getBoolean("success")
            if (success) {
                jsonObject.getString("userState").toInt()
            } else -1
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    fun addUser(userInfo: UserInfo): Boolean {
        val future = RequestFuture.newFuture<String>()
        val registerRequest = RegisterRequest(userInfo.userId, userInfo.userPassword, userInfo.userEmail, future)
        queue.add(registerRequest)

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