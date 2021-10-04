package com.example.foodanywhere.businesslogic

import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.User
import com.example.foodanywhere.datatype.Step
import com.example.foodanywhere.repository.CuisineRepository
import com.example.foodanywhere.viewmodel.CuisineViewModel

class CuisineLogic(private val cuisineViewModel: CuisineViewModel? = null, private val callBack: FoodCallBack? = null) {
    private val cuisineRepository = CuisineRepository.get()
    private var stepList: List<Step>? = null
    private lateinit var iterator: ListIterator<Step>

    fun onCuisineClick(cuisine: Cuisine) {
        User.currentCuisine = cuisine.name
        callBack?.transactFragment()
    }

    fun onViewStepClick() {
        Thread {
            stepList = cuisineRepository.getStepList(User.currentCuisine)
                cuisineViewModel!!.stepListLiveData.postValue(stepList)
                iterator = stepList!!.listIterator()
                cuisineViewModel.setStep(iterator.next())
        }.start()
    }
    fun onNextStepClick() {
        cuisineViewModel!!.setNextStep()
    }

    fun onViewReviewClick() {
        callBack?.transactFragment()
    }
}