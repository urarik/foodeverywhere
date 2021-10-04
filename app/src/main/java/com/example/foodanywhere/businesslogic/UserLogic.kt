package com.example.foodanywhere.businesslogic

import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.MainActivity
import com.example.foodanywhere.User
import com.example.foodanywhere.datatype.UserInfo
import com.example.foodanywhere.repository.UserRepository
import com.example.foodanywhere.viewmodel.UserViewModel

class UserLogic(private val viewModel: UserViewModel, private val callback: FoodCallBack? = null) {
    private val userRepository = UserRepository.get()

    fun onLoginClick(userInfo: UserInfo) {
        val userID = userInfo.userId
        val userPassword = userInfo.userPassword
        User.userId = userID
        Thread {
            viewModel.userStateLiveData.postValue(userRepository.getUserState(userID, userPassword))
        }.start()
    }

    fun onRegisterClick(userInfo: UserInfo) {
        Thread {
            viewModel.isAddedLiveData.postValue(userRepository.addUser(userInfo))
        }.start()
    }

    fun onViewRegisterClick() {
        callback!!.transactFragment()
    }

    fun onLogoutClick() {
        User.isLogin = false
        User.userState = 0

        MainActivity.manageCook.isVisible = false
        MainActivity.manageCuisine.isVisible = false
        MainActivity.applyCuisine.isVisible = false
        callback!!.transactFragment()
    }
}