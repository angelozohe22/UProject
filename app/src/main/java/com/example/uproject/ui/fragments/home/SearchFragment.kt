package com.example.uproject.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.uproject.R
import com.example.uproject.common.utils.afterTextChanged
import com.example.uproject.common.utils.makeClearableEditText
import com.example.uproject.databinding.FragmentHomeBinding
import com.example.uproject.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textInputSearchValidation()
    }

    private fun textInputSearchValidation(){
        val etSearchView = binding.etSearchView
        etSearchView.makeClearableEditText()
    }

}