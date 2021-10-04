package com.example.foodanywhere.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ApplyViewModel(): ViewModel() {
    var counter: Int = 0
    val isStepAdded: MutableLiveData<Boolean> = MutableLiveData()
    val isStepDone: MutableLiveData<Boolean> = MutableLiveData()
    val isCuisineAdded: MutableLiveData<Boolean> = MutableLiveData()
    val isApplicantAdded: MutableLiveData<Boolean> = MutableLiveData()

}