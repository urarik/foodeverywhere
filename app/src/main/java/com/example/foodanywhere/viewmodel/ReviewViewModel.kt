package com.example.foodanywhere.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodanywhere.datatype.Review
import com.example.foodanywhere.repository.ReviewRepository

class ReviewViewModel: ViewModel() {
    private val reviewRepository = ReviewRepository.get()

    val reviewListLiveData: MutableLiveData<List<Review>> = MutableLiveData()
    val isAddedLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun setReviewListLiveData(nation: String, name: String) {
        Thread{
            reviewListLiveData.postValue(reviewRepository.getReviewList(nation, name))
        }.start()
    }
}