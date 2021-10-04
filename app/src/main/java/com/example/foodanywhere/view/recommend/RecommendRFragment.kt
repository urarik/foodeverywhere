package com.example.foodanywhere.view.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.ImageUtil
import com.example.foodanywhere.R
import com.example.foodanywhere.businesslogic.RecommendLogic
import com.example.foodanywhere.databinding.FragmentRecommendRBinding
import com.example.foodanywhere.view.cuisine.CuisineFragment
import com.example.foodanywhere.viewmodel.RecommendViewModel
import com.google.android.material.snackbar.Snackbar

class RecommendRFragment : Fragment() {
    private val recommendViewModel: RecommendViewModel by lazy {
        ViewModelProvider(activity as ViewModelStoreOwner).get(RecommendViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val recommendRBinding: FragmentRecommendRBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_recommend_r, container, false)
        val view = recommendRBinding.root

        recommendRBinding.recommendLogic = RecommendLogic(object: FoodCallBack {
            override fun transactFragment() {
                parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, CuisineFragment())
                        .addToBackStack(null)
                        .commit()
            }

            override fun loadImage() {}
        }, recommendViewModel)

        recommendViewModel.setCuisineListLiveData()

        recommendViewModel.cuisineListLiveData.observe(viewLifecycleOwner, Observer {
            it?.run {
                recommendViewModel.setCuisineLiveData()
            } ?: run {
                Snackbar.make(view, "추천 리스트 로딩 실패", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                parentFragmentManager
                    .popBackStack()
            }
        })
        recommendViewModel.cuisineLiveData.observe(viewLifecycleOwner, Observer {
            recommendRBinding.imageFlagRecommendR.setImageResource(ImageUtil.getFlag(it.nation))
            recommendRBinding.imageRecommendR.setImageBitmap(it.img)
            recommendRBinding.cuisine = it
        })

        return view
    }
}