package com.example.foodanywhere.view.apply

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.R
import com.example.foodanywhere.businesslogic.ApplyLogic
import com.example.foodanywhere.databinding.FragmentAddCuisineBinding
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.view.nation.NationListFragment
import com.example.foodanywhere.viewmodel.ApplyViewModel
import com.google.android.material.snackbar.Snackbar

import kotlin.collections.ArrayList


class CuisineApplyFragment : Fragment() {
    val cuisine: Cuisine = Cuisine("","", characteristicList = ArrayList<Boolean>().apply {
        for(i in 0 until 10) this.add(false)
    })
    lateinit var imageView: ImageView
    private val applyViewModel: ApplyViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val cuisineAddBinding: FragmentAddCuisineBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_cuisine, container, false)
        val view = cuisineAddBinding.root
        imageView = cuisineAddBinding.imageAddCuisine

        cuisineAddBinding.cuisine = cuisine
        cuisineAddBinding.applyLogic = ApplyLogic(applyViewModel, object : FoodCallBack {
            override fun transactFragment() {}
            
            override fun loadImage() {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_MIME_TYPES, arrayListOf("image/jpeg", "image/png"))
                }
                startActivityForResult(intent, REQUEST_IMAGE)
            }
        })
        
        applyViewModel.isCuisineAdded.observe(viewLifecycleOwner, Observer {
            if(it) {
                Snackbar.make(view, "성공", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, StepsApplyFragment())
                        .addToBackStack(null)
                        .commit()
            } else {
                Snackbar.make(view, "실패", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
        })

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK)
            data?.data else throw IllegalAccessException("No image!")

        var bitmap =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                selectedImage!!
                        )
                )
                else MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        selectedImage
                )

        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true)
        cuisine.img = bitmap
        Glide.with(this).load(selectedImage).into(imageView)
    }

    companion object {
        const val REQUEST_IMAGE = 7777
    }

}