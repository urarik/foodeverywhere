package com.example.foodanywhere.businesslogic

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.User
import com.example.foodanywhere.datatype.Applicant
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.datatype.Ingredient
import com.example.foodanywhere.datatype.Step
import com.example.foodanywhere.repository.ApplyRepository
import com.example.foodanywhere.viewmodel.ApplyViewModel

class ApplyLogic(private val applyViewModel: ApplyViewModel, private val callback: FoodCallBack): BaseObservable()  {
    private val applyRepository = ApplyRepository.get()
    lateinit var ingredientString: String

    fun onCuisineLoadImageClick() {
        callback.loadImage()
    }
    fun stepLoadImageClick() {
        callback.loadImage()
    }
    fun applicantLoadImageClick() {
        callback.loadImage()
    }

    fun onAddStepClick(cuisine: Cuisine) {
        Thread {
            val name = cuisine.name
            User.currentCuisine = name

            val ingredientList = mutableListOf<Ingredient>()
            val ingredientStringList = ingredientString.split(":", ",")
            for (i in ingredientStringList.indices step 2) {
                ingredientList.add(
                        Ingredient(
                                ingredientStringList[i].trim(),
                                ingredientStringList[i + 1].trim()
                        )
                )
            }
            applyRepository.addIngredient(name, ingredientList)
            applyViewModel.counter = 0
            applyViewModel.isCuisineAdded.postValue(applyRepository.addUnConfirmedCuisine(cuisine))
        }.start()
    }
    fun onNextStepClick(step: Step) {
        Thread {
            step.counter = applyViewModel.counter++
            applyViewModel.isStepAdded.postValue(applyRepository.addStep(User.currentCuisine, step))
        }.start()

    }
    fun onStepDoneClick(step: Step) {
        Thread {
            step.counter = -1
            applyViewModel.isStepDone.postValue(applyRepository.addStep(User.currentCuisine, step))
        }.start()
    }
    fun onApplicantDoneClick(applicant: Applicant) {
        Thread {
            applyViewModel.isApplicantAdded.postValue(applyRepository.addApplicant(applicant))
        }.start()
    }

    @Bindable
    fun getIngredient(): String {
        return ""
    }
    fun setIngredient(value: String) {
        ingredientString =  value
    }
}