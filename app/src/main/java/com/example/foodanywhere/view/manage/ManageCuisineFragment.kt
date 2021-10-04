package com.example.foodanywhere.view.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.R
import com.example.foodanywhere.User
import com.example.foodanywhere.databinding.FragmentNationListBinding
import com.example.foodanywhere.databinding.ItemCuisinesBinding
import com.example.foodanywhere.ImageUtil
import com.example.foodanywhere.view.cuisine.CuisineFragment
import com.example.foodanywhere.businesslogic.CuisineLogic
import com.example.foodanywhere.viewmodel.ManageViewModel
import com.google.android.material.snackbar.Snackbar

class ManageCuisineFragment : Fragment() {

    private val manageViewModel: ManageViewModel by lazy {
        ViewModelProvider(activity as ViewModelStoreOwner).get(ManageViewModel::class.java)
    }
    private lateinit var adapter: CuisineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cuisineListBinding: FragmentNationListBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_nation_list, container, false)
        val view = cuisineListBinding.root
        User.isManaged = true
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        manageViewModel.unConfirmedCuisineListLiveData.observe(viewLifecycleOwner, Observer {
            it?.run {
                adapter = CuisineAdapter(it)
                recyclerView.adapter = adapter
            } ?: run {
                Snackbar.make(view, "음식 신청 리스트 로딩 실패", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                parentFragmentManager
                    .popBackStack()
            }
        })
        manageViewModel.setUnConfirmedCuisineList()

        return view
    }

    private inner class CuisineHolder(val itemCuisinesBinding: ItemCuisinesBinding) :
        RecyclerView.ViewHolder(itemCuisinesBinding.root) {

        fun bind(cuisine: Cuisine) {
            itemCuisinesBinding.cuisine = cuisine
            itemCuisinesBinding.itemCuisineFlag.setImageResource(ImageUtil.getFlag(cuisine.nation))
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

    private inner class CuisineAdapter(val cuisines: List<Cuisine>) :
        RecyclerView.Adapter<CuisineHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisineHolder {
            val itemCuisinesBinding =
                ItemCuisinesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CuisineHolder(itemCuisinesBinding)
        }

        override fun getItemCount(): Int = cuisines.size

        override fun onBindViewHolder(holder: CuisineHolder, position: Int) {
            val cuisine = cuisines[position]
            holder.bind(cuisine)
        }

    }
}