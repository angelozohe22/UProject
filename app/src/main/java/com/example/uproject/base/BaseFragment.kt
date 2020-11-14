package com.example.uproject.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment<T: ViewBinding>(@LayoutRes contentLayoutId: Int) :Fragment(contentLayoutId) {
    //Declaración del binding
    protected var _binding: T? = null
    protected val binding get() = _binding!!

    //La inicialización se encarga el sistema al usar el constructor de Fragment

    //Liberamos el binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}