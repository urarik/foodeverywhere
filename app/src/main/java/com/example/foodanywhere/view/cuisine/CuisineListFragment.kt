package com.example.foodanywhere.view.cuisine

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
import com.example.foodanywhere.businesslogic.CuisineLogic
import com.example.foodanywhere.databinding.FragmentNationListBinding
import com.example.foodanywhere.databinding.ItemCuisinesBinding
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.ImageUtil
import com.example.foodanywhere.viewmodel.CuisineViewModel
import com.google.android.material.snackbar.Snackbar

class CuisineListFragment : Fragment() {

    private val cuisineViewModel: CuisineViewModel by viewModels()
    private lateinit var adapter: CuisineAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val nationListBinding: FragmentNationListBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_nation_list, container, false)
        val view = nationListBinding.root
        User.isManaged = false

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        cuisineViewModel.cuisineListLiveData.observe(viewLifecycleOwner, Observer {
            it?.run {
                adapter = CuisineAdapter(cuisineViewModel.cuisineListLiveData.value!!)
                recyclerView.adapter = adapter
            } ?: run {
                Snackbar.make(view, "음식 리스트 로딩 실패", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                parentFragmentManager
                    .popBackStack()
            }
        })
        cuisineViewModel.setCuisineList(User.currentNation)


        return view
    }

    private inner class CuisineHolder(val itemCuisinesBinding: ItemCuisinesBinding) : RecyclerView.ViewHolder(itemCuisinesBinding.root) {

        fun bind(string: String) {
            itemCuisinesBinding.cuisine = Cuisine(nation = User.currentNation, name = string)
            itemCuisinesBinding.itemCuisineFlag.setImageResource(ImageUtil.getFlag(User.currentNation))
            itemCuisinesBinding.cuisineLogic = CuisineLogic(callBack = object : FoodCallBack {
                override fun transactFragment() {
                    parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, CuisineFragment())
                            .addToBackStack(null)
                            .commit()
                }

                override fun loadImage() {}
            })
        }
    }

    private inner class CuisineAdapter(val names: List<String>) :
            RecyclerView.Adapter<CuisineHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisineHolder {
            val itemCuisinesBinding = ItemCuisinesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CuisineHolder(itemCuisinesBinding)
        }

        override fun getItemCount(): Int = names.size

        override fun onBindViewHolder(holder: CuisineHolder, position: Int) {
            val name = names[position]
            holder.bind(name)
        }

    }
}