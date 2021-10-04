package com.example.foodanywhere.view.nation

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
import com.example.foodanywhere.datatype.Nation
import com.example.foodanywhere.R
import com.example.foodanywhere.databinding.FragmentNationListBinding
import com.example.foodanywhere.databinding.ItemNationsBinding
import com.example.foodanywhere.businesslogic.NationLogic
import com.example.foodanywhere.ImageUtil
import com.example.foodanywhere.view.cuisine.CuisineListFragment
import com.example.foodanywhere.viewmodel.NationViewModel

class NationListFragment : Fragment() {

    private var adapter: NationAdapter? = NationAdapter(emptyList())
    private val nationListViewModel: NationViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val nationListBinding: FragmentNationListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_nation_list, container, false)
        val view = nationListBinding.root
        with(nationListBinding.recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nationListViewModel.nationListLiveData.observe(
                viewLifecycleOwner,
                Observer { nations ->
                    nations?.let {
                        updateUI(nations, view.findViewById(R.id.recycler))
                        for (nation in nations)
                            nationListViewModel.isExpanded[nation.name] = false
                    }
                }
        )

    }

    private inner class NationHolder(val itemNationsBinding: ItemNationsBinding) : RecyclerView.ViewHolder(itemNationsBinding.root) {
        fun bind(nation: Nation) {
            itemNationsBinding.nation = nation
            itemNationsBinding.nationLogic =
                    NationLogic(object : FoodCallBack {
                        override fun transactFragment() {
                            parentFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.nav_host_fragment, CuisineListFragment())
                                    .addToBackStack(null)
                                    .commit()
                        }

                        override fun loadImage() {}
                    },
                            nationListViewModel,
                            itemNationsBinding.layoutExpand
                    )
            itemNationsBinding.itemFlag.setImageResource(ImageUtil.getFlag(nation.name))
            itemNationsBinding.nationImage.setImageResource(ImageUtil.getImg(nation.name))
        }
    }

    private inner class NationAdapter(val nations: List<Nation>) :
            RecyclerView.Adapter<NationHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationHolder {
            val itemNationsBinding = ItemNationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NationHolder(itemNationsBinding)
        }

        override fun getItemCount(): Int = nations.size

        override fun onBindViewHolder(holder: NationHolder, position: Int) {
            val nation = nations[position]
            holder.bind(nation)
        }

    }

    private fun updateUI(nations: List<Nation>, recyclerView: RecyclerView) {
        adapter = NationAdapter(nations)
        recyclerView.adapter = adapter
    }

}

