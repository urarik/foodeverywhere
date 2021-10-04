package com.example.foodanywhere.businesslogic

import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.User
import com.example.foodanywhere.datatype.Review
import com.example.foodanywhere.repository.ReviewRepository
import com.example.foodanywhere.viewmodel.ReviewViewModel

class ReviewLogic(private val reviewViewModel: ReviewViewModel? = null, private val callBack: FoodCallBack? = null) {
    private val reviewRepository = ReviewRepository.get()

    fun onAddReviewClick() {
        callBack!!.transactFragment()
    }

    fun onReviewDoneClick(review: Review) {
        Thread {
            reviewViewModel!!.isAddedLiveData.postValue(reviewRepository.addReview(User.currentNation, User.currentCuisine, review))
        }.start()
    }
}