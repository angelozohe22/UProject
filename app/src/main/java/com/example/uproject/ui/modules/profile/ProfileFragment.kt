package com.example.uproject.ui.modules.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uproject.core.preferences
import com.example.uproject.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        setupProfile()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupProfile(){
        val name  = preferences.username
        val email = preferences.email
        val phone = preferences.phone
        binding.tvUsernameProfile.setText(name)
        binding.tvMailProfile.setText(email)
        binding.tvPhoneProfile.setText(phone)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}