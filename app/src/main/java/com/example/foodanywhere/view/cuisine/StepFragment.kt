package com.example.foodanywhere.view.cuisine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.R
import com.example.foodanywhere.User
import com.example.foodanywhere.businesslogic.CuisineLogic
import com.example.foodanywhere.businesslogic.ManageLogic
import com.example.foodanywhere.databinding.FragmentStepsBinding
import com.example.foodanywhere.viewmodel.CuisineViewModel
import com.example.foodanywhere.view.manage.ManageCuisineFragment
import com.example.foodanywhere.view.review.ReviewListFragment
import com.example.foodanywhere.viewmodel.ManageViewModel
import com.google.android.material.snackbar.Snackbar

class StepFragment: Fragment() {
    private val cuisineViewModel: CuisineViewModel by lazy {
        ViewModelProvider(activity as ViewModelStoreOwner).get(CuisineViewModel::class.java)
    }
    private val manageViewModel: ManageViewModel by lazy {
        ViewModelProvider(activity as ViewModelStoreOwner).get(ManageViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val cuisineBinding: FragmentStepsBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_steps, container, false)
        val view = cuisineBinding.root
        cuisineBinding.cuisineLogic = CuisineLogic(cuisineViewModel, object : FoodCallBack {
            override fun transactFragment() {
                parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, ReviewListFragment())
                        .addToBackStack(null)
                        .commit()
            }

            override fun loadImage() {}
        })
        cuisineBinding.manageLogic = ManageLogic(manageViewModel)

        cuisineViewModel.stepLiveData.observe(viewLifecycleOwner, Observer { step ->
            step?.run {
                cuisineBinding.imageStep.setImageBitmap(step.bitmap)
                cuisineBinding.step = step
            }
        })


        cuisineViewModel.isEndLiveData.observe(viewLifecycleOwner, Observer {
            if(it) {
                if(User.isManaged) {
                    cuisineBinding.buttonNextStep.visibility = ViewGroup.GONE
                    cuisineBinding.buttonCuisineYes.visibility = ViewGroup.VISIBLE
                    cuisineBinding.buttonCuisineNo.visibility = ViewGroup.VISIBLE
                } else {
                    cuisineBinding.buttonNextStep.visibility = ViewGroup.GONE
                    cuisineBinding.buttonViewReview.visibility = ViewGroup.VISIBLE
                }
            }
        })

        manageViewModel.isCuisineManaged.observe(viewLifecycleOwner, Observer {
            if(manageViewModel.getCuisineState()) {
                if (it) {
                    Snackbar.make(view, "음식 추가 신청 성공", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment,
                                    ManageCuisineFragment()
                            )
                            .addToBackStack(null)
                            .commit()
                } else {
                        Snackbar.make(view, "음식 추가 신청 실패", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                        parentFragmentManager
                            .popBackStack()
                    }
            }
        })

        return view
    }


}