package com.example.uproject.ui.fragments.restorepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.uproject.R
import com.example.uproject.databinding.DialogForgotPasswordBinding
import com.example.uproject.data.firebase.auth.FirebaseDataSourceImpl
import com.example.uproject.domain.auth.AuthRepositoryImpl
import com.example.uproject.ui.viewmodels.RestorePasswordViewModel
import com.example.uproject.ui.viewmodels.factory.AuthViewModelFactory
import androidx.lifecycle.Observer
import com.example.uproject.common.utils.*
import com.example.uproject.core.Resource

class RecoverPasswordFragment: DialogFragment() {

    private var _binding: DialogForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<RestorePasswordViewModel>{
        AuthViewModelFactory(
            AuthRepositoryImpl(
                FirebaseDataSourceImpl()
            )
        )
    }

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
            btnRestorePassword.apply {
                isEnabled = false
                setBackgroundResource(R.drawable.btn_corner_dissable)
            }
        }
        textFieldForgotPassValidation()
    }

    private fun textFieldForgotPassValidation(){
        val btnRecoverPassword  = binding.btnRestorePassword
        val cetEmail              = binding.cetEmailRecoverPassword
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

                setOnClickListener {
                    setupRestorePasswordObserver(email)
                }

            }
        }
        etEmail.addTextChangedListener(validate)
    }

    private fun setupRestorePasswordObserver(email: String){
        viewModel.restorePassword(email).observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {
                        showProgress()
                    }
                    is Resource.Success -> {
                        hideProgress()
                        if(result.data) {
                            toast("Verifique su correo electronico para reestablecer su contraseña")
                            dialog?.dismiss()
                        }
                    }
                    is Resource.Failure -> {
                        hideProgress()
                        val exception = authErrorMessage(result.exception)
                        toast(exception)
                    }
                }
            }
        })
    }

    private fun showProgress(){
        with(binding){
            btnRestorePassword.visibility = View.GONE
            progressBarRestorePass.visibility = View.VISIBLE
        }
    }

    private fun hideProgress(){
        with(binding){
            btnRestorePassword.visibility = View.VISIBLE
            progressBarRestorePass.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}