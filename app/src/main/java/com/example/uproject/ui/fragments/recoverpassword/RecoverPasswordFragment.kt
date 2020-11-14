package com.example.uproject.ui.fragments.recoverpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.uproject.R
import com.example.uproject.databinding.DialogForgotPasswordBinding
import com.example.uproject.utils.afterTextChanged
import com.example.uproject.utils.isEmailValid
import com.example.uproject.utils.isNullOrEmpty

class RecoverPasswordFragment: DialogFragment() {

    private var _binding: DialogForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnRecoverPassword.apply {
                isEnabled = false
                setBackgroundResource(R.drawable.btn_corner_dissable)
            }
        }
        textFieldForgotPassValidation()
    }

    private fun textFieldForgotPassValidation(){
        val btnRecoverPassword   = binding.btnRecoverPassword
        val cetEmail             = binding.cetEmailRecoverPassword
        val etEmail              = binding.etEmailRecoverPassword

        val validate = afterTextChanged {
            val email = etEmail.text.toString().trim()

            //Only show email error
            if(!isNullOrEmpty(email)){
                cetEmail.error = when{
                    !isEmailValid(email) -> getString(R.string.error_email)
                    else -> null
                }
            }

            btnRecoverPassword.apply {
                isEnabled = !isNullOrEmpty(email)
                        &&  isEmailValid(email)
                if(isEnabled) setBackgroundResource(R.drawable.btn_corner)
                else setBackgroundResource(R.drawable.btn_corner_dissable)
            }
        }
        etEmail.addTextChangedListener(validate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}