package com.example.uproject.ui.login.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.uproject.R
import com.example.uproject.databinding.DialogForgotPasswordBinding
import com.example.uproject.common.utils.*
import com.example.uproject.core.Resource
import com.example.uproject.ui.login.AuthViewModel
import com.example.uproject.ui.login.MainActivity

class RecoverPasswordFragment: DialogFragment() {

    private var _binding: DialogForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogForgotPasswordBinding.inflate(inflater, container, false)

        viewModel = (activity as MainActivity).viewModel

        binding.btnRestorePassword.apply{
            isEnabled = false
            setBackgroundResource(R.drawable.btn_corner_dissable)
        }
        textFieldForgotPassValidation()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        viewModel.restorePassword(email).observe(
            viewLifecycleOwner
        ){
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
                        toast(result.errorMessage)
                    }
                }
            }
        }
    }

    private fun showProgress(){
        binding.apply{
            btnRestorePassword.visibility = View.GONE
            progressBarRestorePass.visibility = View.VISIBLE
        }
    }

    private fun hideProgress(){
        binding.apply{
            btnRestorePassword.visibility = View.VISIBLE
            progressBarRestorePass.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}