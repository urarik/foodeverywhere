package com.example.foodanywhere.businesslogic

import android.util.Log
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.User
import com.example.foodanywhere.datatype.Applicant
import com.example.foodanywhere.repository.ManageRepository
import com.example.foodanywhere.viewmodel.ManageViewModel

class ManageLogic(private val manageViewModel: ManageViewModel? = null, private val callBack: FoodCallBack? = null) {
    private val manageRepository = ManageRepository.get()

    fun onCuisineConfirmed() {
        Thread {
            manageViewModel!!.isCuisineManaged.postValue(manageRepository.confirmCuisine(User.currentCuisine))
        }.start()
    }
    fun onCuisineReject() {
        Thread {
            manageViewModel!!.isCuisineManaged.postValue(manageRepository.deleteCuisine(User.currentCuisine))
        }.start()
    }
    fun onApplicantClick(id: String) {
        manageViewModel?.applicantId = id
        callBack?.transactFragment()
    }
    fun onApplicantConfirmed(applicant: Applicant) {
        Thread {
            Log.d("TAG", "!!")
            manageViewModel!!.isApplicantManaged.postValue(manageRepository.confirmApplicant(applicant.userId))
        }.start()
    }
    fun onApplicantReject(applicant: Applicant) {
        Thread {
            manageViewModel!!.isApplicantManaged.postValue(manageRepository.deleteApplicant(applicant.userId))
        }.start()
    }


}