package com.example.foodanywhere.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.datatype.Ingredient
import com.example.foodanywhere.datatype.Step
import com.example.foodanywhere.User
import com.example.foodanywhere.repository.CuisineRepository

class CuisineViewModel : ViewModel() {
    private val cuisineRepository = CuisineRepository.get()

    val cuisineListLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val ingredientListLiveData: MutableLiveData<List<Ingredient>> = MutableLiveData()
    val characteristicListLiveData: MutableLiveData<List<Boolean>> = MutableLiveData()
    val stepListLiveData: MutableLiveData<List<Step>> = MutableLiveData()
    val cuisineLiveData: MutableLiveData<Cuisine> = MutableLiveData()
    val stepLiveData: MutableLiveData<Step> = MutableLiveData()
    val isEndLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var stepCounter: Int = 0
    var stepsList: List<Step>? = null
        get() = field
        set(value) {
            field = value
        }

    fun setNextStep() {
        setStep(stepsList!![stepCounter++])
    }

    fun setCuisine() {
        Thread {
            stepCounter = 0
            cuisineRepository.getCuisine(User.currentCuisine)?.run {
                cuisineLiveData.postValue(this)
                characteristicListLiveData.postValue(this.characteristicList)
                ingredientListLiveData.postValue(this.ingredientList)
            }
        }.start()
    }


    fun setStep(step: Step) {
        stepCounter++
        Thread {
            isEndLiveData.postValue(step.counter == -1)
            stepLiveData.postValue(step)
        }.start()
    }

    fun setCuisineList(nation: String) {
        Thread {
            cuisineListLiveData.postValue(cuisineRepository.getCuisineList(nation))
        }.start()
    }

}