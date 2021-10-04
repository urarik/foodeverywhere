package com.example.foodanywhere.repository

import android.content.Context
import com.example.foodanywhere.dao.user.UserDao
import com.example.foodanywhere.datatype.UserInfo

class UserRepository private constructor(context: Context){
    private val userDao: UserDao = UserDao(context)

    fun getUserState(userID: String, userPassword: String): Int = userDao.getUserState(userID, userPassword)
    fun addUser(userInfo: UserInfo): Boolean = userDao.addUser(userInfo)


    companion object {
        private var INSTANCE: UserRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null)
                INSTANCE =
                    UserRepository(
                        context
                    )
        }
        fun get(): UserRepository {
            return INSTANCE
                ?:
            throw IllegalAccessException("NationRepository must be initialized")
        }
    }
}