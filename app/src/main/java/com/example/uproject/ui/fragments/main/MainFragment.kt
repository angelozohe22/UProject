package com.example.uproject.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.uproject.R
import com.example.uproject.base.BaseFragment
import com.example.uproject.databinding.FragmentMainBinding
import com.example.uproject.common.utils.setNavigationBarColor
import com.example.uproject.common.utils.setStatusBarColor

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        setStatusBarColor()
        setNavigationBarColor()

        with(binding){
            btnMainSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_signUpFragment)
            }
            btnMainSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_signInFragment)
            }
        }

    }



}