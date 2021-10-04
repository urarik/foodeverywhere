package com.example.foodanywhere.view.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.foodanywhere.R
import com.example.foodanywhere.businesslogic.ReviewLogic
import com.example.foodanywhere.databinding.FragmentAddReviewBinding
import com.example.foodanywhere.datatype.Review
import com.example.foodanywhere.viewmodel.ReviewViewModel
import com.google.android.material.snackbar.Snackbar

class ReviewAddFragment: Fragment() {
    private val reviewViewModel: ReviewViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val reviewAddBinding: FragmentAddReviewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_review, container, false)

        val view = reviewAddBinding.root

        reviewAddBinding.reviewLogic = ReviewLogic(reviewViewModel)
        reviewAddBinding.review = Review("", "")

        reviewViewModel.isAddedLiveData.observe(viewLifecycleOwner, Observer {
            if(it) {
                Snackbar.make(view, "리뷰 작성 성공", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, ReviewListFragment())
                        .addToBackStack(null)
                        .commit()
            } else {
                Snackbar.make(view, "리뷰 작성 실패", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                parentFragmentManager
                    .popBackStack()
            }
        })
        return view
    }

}