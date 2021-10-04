package com.example.foodanywhere.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    val menuString: MutableLiveData<String> = MutableLiveData<String>().apply { value =  "로그인하지 않았습니다."}
    val userStateLiveData: MutableLiveData<Int> = MutableLiveData()
    val isAddedLiveData: MutableLiveData<Boolean> = MutableLiveData()
}