package com.example.foodanywhere.businesslogic

import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.User
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.viewmodel.RecommendViewModel

class RecommendLogic(private val callback: FoodCallBack? = null, private val viewModel: RecommendViewModel) {

    fun onNextCuisineClick() {
        viewModel.setCuisineLiveData()
    }

    fun onViewCuisineClick(cuisine: Cuisine) {
        User.currentCuisine = cuisine.name
        User.currentNation = cuisine.nation
        callback!!.transactFragment()
    }
}