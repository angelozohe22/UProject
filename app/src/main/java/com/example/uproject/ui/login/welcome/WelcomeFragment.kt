package com.example.uproject.ui.login.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.uproject.R
import com.example.uproject.databinding.FragmentMainBinding
import com.example.uproject.common.utils.setNavigationBarColor
import com.example.uproject.common.utils.setStatusBarColor

class WelcomeFragment : Fragment() { //BaseFragment<FragmentMainBinding>(R.layout.fragment_main)

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        setStatusBarColor(requireActivity())
        setNavigationBarColor(requireActivity())

        binding.apply{
            btnMainSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_signUpFragment)
            }
            btnMainSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_signInFragment)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}