package com.example.foodanywhere.view.cuisine

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.foodanywhere.*
import com.example.foodanywhere.databinding.FragmentCuisineBinding
import com.example.foodanywhere.businesslogic.CuisineLogic
import com.example.foodanywhere.viewmodel.CuisineViewModel
import com.google.android.material.snackbar.Snackbar

class CuisineFragment : Fragment() {
    private val cuisineViewModel: CuisineViewModel by lazy {
        ViewModelProvider(activity as ViewModelStoreOwner).get(CuisineViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cuisineBinding: FragmentCuisineBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cuisine, container, false)
        val view = cuisineBinding.root
        cuisineViewModel.setCuisine()

        cuisineBinding.cuisineLogic = CuisineLogic(cuisineViewModel)
        cuisineViewModel.cuisineLiveData.observe(viewLifecycleOwner, Observer {
            it?.run {
                cuisineBinding.cuisine = it
                cuisineBinding.characteristics = it.characteristicList
                cuisineBinding.imageCuisine.setImageBitmap(it.img)

                for (i in it.ingredientList!!.indices) {
                    val (ingredientName, quantity) = it.ingredientList!![i]

                    val textView = TextView(context)
                    textView.text = "$ingredientName\t\t:$quantity"
                    textView.textSize =
                        resources.getDimension(R.dimen.cuisine_font) / resources.displayMetrics.density
                    textView.setTextColor(resources.getColor(R.color.black, null))

                    if (i % 2 == 0) cuisineBinding.linearIngredient1.addView(textView)
                    else cuisineBinding.linearIngredient2.addView(textView)
                }
            } ?: run {
                Snackbar.make(view, "음식 로딩 실패", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                parentFragmentManager
                    .popBackStack()
            }
        })
        cuisineViewModel.stepListLiveData.observe(viewLifecycleOwner, Observer {
            it?.run {
                with(cuisineViewModel) {
                    stepsList = it
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, StepFragment())
                        .addToBackStack(null)
                        .commit()
                    //stepsList = null
                }
                cuisineViewModel.stepListLiveData.postValue(null)
            }
        })

        return view
    }
}