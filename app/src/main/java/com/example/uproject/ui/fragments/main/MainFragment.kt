package com.example.uproject.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.uproject.R
import com.example.uproject.utils.setNavigationBarColor
import com.example.uproject.utils.setStatusBarColor

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor(R.color.color_Uranian_Blue)
        setNavigationBarColor(R.color.color_Unbleached_Silk)

        val btn  = view.findViewById<AppCompatButton>(R.id.btn_main_sign_up)
        val btn2 = view.findViewById<AppCompatButton>(R.id.btn_main_sign_in)
        btn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_signUpFragment2)
        }
        btn2.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_signInFragment2)
        }

    }
}