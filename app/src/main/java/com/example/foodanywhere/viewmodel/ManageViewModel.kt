package com.example.foodanywhere.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodanywhere.datatype.Applicant
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.repository.ManageRepository

class ManageViewModel : ViewModel() {
    private val manageRepository =  ManageRepository.get()
    val unConfirmedCuisineListLiveData = MutableLiveData<List<Cuisine>>()
    val applicantListLiveData = MutableLiveData<List<String>>()
    val applicantLiveData = MutableLiveData<Applicant>()
    val isCuisineManaged: MutableLiveData<Boolean> = MutableLiveData()
    val isApplicantManaged: MutableLiveData<Boolean> = MutableLiveData()
    var applicantId: String = ""
    private var applicantState = true
    private var cuisineState = true

    fun getApplicantState():Boolean {
        val value = applicantState
        applicantState = !applicantState
        return value
    }

    fun getCuisineState():Boolean {
        val value = cuisineState
        cuisineState = !cuisineState
        return value
    }
    fun setUnConfirmedCuisineList() {
        Thread {
            unConfirmedCuisineListLiveData.postValue(manageRepository.getUnConfirmedCuisineList())
        }.start()
    }

    fun setApplicantList() {
        Thread {
            applicantListLiveData.postValue(manageRepository.getApplicantList())
        }.start()
    }

    fun setApplicant(id: String) {
        Thread {
            applicantLiveData.postValue(manageRepository.getApplicant(id))
        }.start()
    }
}