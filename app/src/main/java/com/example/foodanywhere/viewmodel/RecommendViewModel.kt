package com.example.foodanywhere.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.repository.ManageRepository
import com.example.foodanywhere.repository.RecommendRepository

class RecommendViewModel : ViewModel() {
    private val recommendRepository =  RecommendRepository.get()
    val cuisineListLiveData: MutableLiveData<List<Cuisine>> = MutableLiveData()
    val cuisineLiveData: MutableLiveData<Cuisine> = MutableLiveData()

    fun setCuisineListLiveData() {
        Thread{
            cuisineListLiveData.postValue(recommendRepository.getCuisineList())
        }.start()
    }

    fun setCuisineLiveData() {
        cuisineLiveData.value = cuisineListLiveData.value!![(cuisineListLiveData.value!!.indices).random()]
    }


}