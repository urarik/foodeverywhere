package com.example.foodanywhere.view.manage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.R
import com.example.foodanywhere.User
import com.example.foodanywhere.businesslogic.CuisineLogic
import com.example.foodanywhere.businesslogic.ManageLogic
import com.example.foodanywhere.databinding.FragmentApplicantBinding
import com.example.foodanywhere.databinding.FragmentStepsBinding
import com.example.foodanywhere.view.nation.NationListFragment
import com.example.foodanywhere.view.review.ReviewListFragment
import com.example.foodanywhere.viewmodel.CuisineViewModel
import com.example.foodanywhere.viewmodel.ManageViewModel
import com.google.android.material.snackbar.Snackbar

class ApplicantFragment: Fragment() {
    private val manageViewModel: ManageViewModel by lazy {
        ViewModelProvider(activity as ViewModelStoreOwner).get(ManageViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val applicantBinding: FragmentApplicantBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_applicant, container, false)
        val view = applicantBinding.root
        applicantBinding.manageLogic = ManageLogic(manageViewModel)

        manageViewModel.setApplicant(manageViewModel.applicantId)

        manageViewModel.applicantLiveData.observe(viewLifecycleOwner, Observer {
            it?.run {
                applicantBinding.applicant = it
                applicantBinding.imageApplicant.setImageBitmap(it.image)
            } ?: run {
                Snackbar.make(view, "신청자 로딩 실패", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                parentFragmentManager
                    .popBackStack()
            }
        })
        manageViewModel.isApplicantManaged.observe(viewLifecycleOwner, Observer {
            if (manageViewModel.getApplicantState()) {
                if (it) {
                    Snackbar.make(view, "신청자 요청 처리 성공", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, NationListFragment())
                            .addToBackStack(null)
                            .commit()
                } else {
                    manageViewModel.isApplicantManaged.postValue(null)
                    Snackbar.make(view, "신청자 요청 처리 실패", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                }
            }
        })
        return view
    }


}