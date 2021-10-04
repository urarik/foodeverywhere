package com.example.foodanywhere.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.MainActivity
import com.example.foodanywhere.R
import com.example.foodanywhere.User
import com.example.foodanywhere.databinding.FragmentLoginAlreadyBinding
import com.example.foodanywhere.databinding.FragmentLoginBinding
import com.example.foodanywhere.datatype.UserInfo
import com.example.foodanywhere.businesslogic.UserLogic
import com.example.foodanywhere.view.nation.NationListFragment
import com.example.foodanywhere.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if(User.isLogin) {
            val loginBinding: FragmentLoginAlreadyBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login_already, container, false)
            loginBinding.userLogic = UserLogic(userViewModel, object : FoodCallBack {
                        override fun transactFragment() {
                            parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, LoginFragment())
                                .addToBackStack(null)
                                .commit()
                        }

                override fun loadImage() {}
            })

            return loginBinding.root
        }

        val loginBinding: FragmentLoginBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)
        val view = loginBinding.root
        loginBinding.userInfo = UserInfo()
        loginBinding.userLogic = UserLogic(userViewModel, object : FoodCallBack {
            override fun transactFragment() {
                        parentFragmentManager
                            .beginTransaction()
                            .replace( R.id.nav_host_fragment, RegisterFragment())
                            .addToBackStack(null)
                            .commit()
                    }

            override fun loadImage() {}
        })

        userViewModel.menuString.observe(viewLifecycleOwner, Observer<String> { newText ->
            MainActivity.loginText.text = newText
        } )

        userViewModel.userStateLiveData.observe(viewLifecycleOwner, Observer<Int> {
            if (it != -1) {
                Snackbar.make(view, "로그인 성공", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                val str = "${User.userId}님 환영합니다!\n 상태: ${when (it) {
                    1 -> {
                        MainActivity.applyCook.isVisible = true
                        "유저"
                    }
                    2 -> {
                        MainActivity.applyCuisine.isVisible = true
                        "요리사"
                    }
                    3 -> {
                        MainActivity.manageCook.isVisible = true
                        MainActivity.manageCuisine.isVisible = true
                        "관리자"
                    }
                    else -> "에러"
                }}"
                userViewModel.menuString.value = str
                User.isLogin = true

                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment,
                        NationListFragment()
                    )
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }else {
                Snackbar.make(view, "로그인 실패", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        })


        return view
    }
}