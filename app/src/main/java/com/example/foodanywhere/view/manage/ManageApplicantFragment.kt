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
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.R
import com.example.foodanywhere.User
import com.example.foodanywhere.databinding.FragmentNationListBinding
import com.example.foodanywhere.businesslogic.ManageLogic
import com.example.foodanywhere.databinding.ItemApplicantBinding
import com.example.foodanywhere.viewmodel.ManageViewModel
import com.google.android.material.snackbar.Snackbar

class ManageApplicantFragment : Fragment() {

    private val manageViewModel: ManageViewModel by lazy {
        ViewModelProvider(activity as ViewModelStoreOwner).get(ManageViewModel::class.java)
    }
    private lateinit var adapter: ApplicantAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val applicantListBinding: FragmentNationListBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_nation_list, container, false)
        val view = applicantListBinding.root
        User.isManaged = true
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        manageViewModel.setApplicantList()
        manageViewModel.applicantListLiveData.observe(viewLifecycleOwner, Observer {
            it?.run {
                adapter = ApplicantAdapter(it)
                recyclerView.adapter = adapter
            } ?: run {
                Snackbar.make(view, "신청자 리스트 로딩 실패", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                parentFragmentManager
                    .popBackStack()
            }
        })

        return view
    }

    private inner class ApplicantHolder(val itemApplicantBinding: ItemApplicantBinding) : RecyclerView.ViewHolder(itemApplicantBinding.root) {

        fun bind(id: String) {
            itemApplicantBinding.id = id
            itemApplicantBinding.manageLogic = ManageLogic(manageViewModel, object : FoodCallBack {
                override fun transactFragment() {
                    parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, ApplicantFragment())
                            .addToBackStack(null)
                            .commit()
                }

                override fun loadImage() {}
            })
        }
    }

    private inner class ApplicantAdapter(val ids: List<String>) :
            RecyclerView.Adapter<ApplicantHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantHolder {
            val itemApplicantBinding = ItemApplicantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ApplicantHolder(itemApplicantBinding)
        }

        override fun getItemCount(): Int = ids.size

        override fun onBindViewHolder(holder: ApplicantHolder, position: Int) {
            val id = ids[position]
            holder.bind(id)
        }

    }
}