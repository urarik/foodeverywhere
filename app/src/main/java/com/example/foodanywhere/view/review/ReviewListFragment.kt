package com.example.foodanywhere.view.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.R
import com.example.foodanywhere.User
import com.example.foodanywhere.businesslogic.ReviewLogic
import com.example.foodanywhere.databinding.FragmentNationListBinding
import com.example.foodanywhere.databinding.ItemReviewBinding
import com.example.foodanywhere.datatype.Review
import com.example.foodanywhere.viewmodel.ReviewViewModel
import com.google.android.material.snackbar.Snackbar

class ReviewListFragment: Fragment() {

    private val reviewViewModel: ReviewViewModel by viewModels()
    private lateinit var adapter: ReviewAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val reviewListBinding: FragmentNationListBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_nation_list, container, false)
        val view = reviewListBinding.root
        if(User.isLogin)
            reviewListBinding.buttonAddReview.visibility = ViewGroup.VISIBLE
        reviewListBinding.reviewLogic = ReviewLogic(reviewViewModel, object : FoodCallBack {
            override fun transactFragment() {
                parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, ReviewAddFragment())
                        .addToBackStack(null)
                        .commit()
            }

            override fun loadImage() {}
        })

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        reviewViewModel.reviewListLiveData.observe(viewLifecycleOwner, Observer {
            it?.run {
                adapter = ReviewAdapter(reviewViewModel.reviewListLiveData.value!!)
                recyclerView.adapter = adapter
            } ?: run {
                Snackbar.make(view, "리뷰 로딩 실패", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                parentFragmentManager
                    .popBackStack()
            }
        })
        reviewViewModel.setReviewListLiveData(User.currentNation, User.currentCuisine)


        return view
    }

    private inner class ReviewHolder(val itemReviewBinding: ItemReviewBinding) : RecyclerView.ViewHolder(itemReviewBinding.root) {

        fun bind(review: Review) {
            itemReviewBinding.review = review
        }
    }

    private inner class ReviewAdapter(val reviews: List<Review>) :
            RecyclerView.Adapter<ReviewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
            val itemReviewBinding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ReviewHolder(itemReviewBinding)
        }

        override fun getItemCount(): Int = reviews.size

        override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
            val review = reviews[position]
            holder.bind(review)
        }

    }
}