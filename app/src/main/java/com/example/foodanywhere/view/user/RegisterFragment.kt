package com.example.foodanywhere.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.foodanywhere.R
import com.example.foodanywhere.databinding.FragmentRegisterBinding
import com.example.foodanywhere.datatype.UserInfo
import com.example.foodanywhere.businesslogic.UserLogic
import com.example.foodanywhere.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val registerBinding: FragmentRegisterBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_register, container, false)
        val view = registerBinding.root
        registerBinding.userLogic = UserLogic(userViewModel)
        registerBinding.userInfo = UserInfo("", "", "")

        userViewModel.isAddedLiveData.observe(viewLifecycleOwner, Observer{
            if(it) {
                Snackbar.make(view, "성공", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, LoginFragment())
                    .addToBackStack(null)
                    .commit()
            } else {
                Snackbar.make(view, "실패", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

        })
        return view
    }
}