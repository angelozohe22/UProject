package com.example.uproject.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.uproject.R
import com.example.uproject.base.BaseFragment
import com.example.uproject.core.Resource
import com.example.uproject.data.firebase.auth.FirebaseDataSourceImpl
import com.example.uproject.databinding.FragmentSignUpBinding
import com.example.uproject.domain.auth.AuthRepositoryImpl
import com.example.uproject.ui.viewmodels.SignUpViewModel
import com.example.uproject.ui.viewmodels.factory.AuthViewModelFactory
import com.example.uproject.common.utils.*

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val viewModel by activityViewModels<SignUpViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                FirebaseDataSourceImpl()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        setStatusBarColor()
        setNavigationBarColor()

        with(binding){
            btnRegisterSignUp.apply {
                isEnabled = false
                setBackgroundResource(R.drawable.btn_corner_dissable)
            }

            btnBackSignUp.setOnClickListener {
                clearFields()
                requireActivity().onBackPressed()
            }
        }

        binding.lblFromSignUpTo.apply {
            val lblQuestion = getString(R.string.lbl_from_register_to_question).getColoredSpanned(getString(R.string.color_Gray_Web))
            val lblAnswer   = getString(R.string.lbl_from_register_to_answer).getColoredSpanned(getString(R.string.color_Brown_Sugar))
            text = HtmlCompat.fromHtml("$lblQuestion $lblAnswer", HtmlCompat.FROM_HTML_MODE_LEGACY)

            setOnClickListener {
                navigateFromSignUpToSignIn()
            }
        }
        textFieldSignUpValidations()
    }

    private fun textFieldSignUpValidations(){
        val btnSignUp   = binding.btnRegisterSignUp
        val cetPhone    = binding.cetPhoneSignUp
        val cetEmail    = binding.cetEmailSignUp
        val cetPassword = binding.cetPasswordSignUp
        val etUsername  = binding.etUsernameSignUp
        val etPhone     = binding.etPhoneSignUp
        val etEmail     = binding.etEmailSignUp
        val etPassword  = binding.etPasswordSignUp

        val validate = afterTextChanged {
            val username = etUsername.text.toString().trim()
            val phone    = etPhone.text.toString().trim()
            val email    = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            //Only show phone error
            if(!isNullOrEmpty(phone)){
                cetPhone.error = when{
                    phone.substring(0,1).toInt() != 9 -> getString(R.string.error_invalid_phone)
                    phone.length < 9 -> getString(R.string.error_phone)
                    else -> null
                }
            }

            //Only show email error
            if(!isNullOrEmpty(email)){
                cetEmail.error = when{
                    !isEmailValid(email) -> getString(R.string.error_email)
                    else -> null
                }
            }

            //Only show password error
            if(!isNullOrEmpty(password)){
                cetPassword.error = when{
                    password.length <= 6 -> getString(R.string.error_password)
                    else -> null
                }
            }

            btnSignUp.apply {
                isEnabled = !isNullOrEmpty(username)
                        &&  !isNullOrEmpty(phone)
                        &&  phone.length == 9
                        &&  !isNullOrEmpty(email)
                        &&  isEmailValid(email)
                        &&  !isNullOrEmpty(password)
                        &&  password.length > 6
                if(isEnabled) setBackgroundResource(R.drawable.btn_corner)
                else setBackgroundResource(R.drawable.btn_corner_dissable)

                setOnClickListener {
                    setupSignUpObserver(username, phone, email, password)
                }
            }
        }
        etUsername.addTextChangedListener(validate)
        etPhone.addTextChangedListener(validate)
        etEmail.addTextChangedListener(validate)
        etPassword.addTextChangedListener(validate)

    }

    private fun setupSignUpObserver(username: String, phone: String, email: String, password: String){
        viewModel.signUp(username, phone, email, password).observe(
            viewLifecycleOwner,
            Observer {
                it?.let { result ->
                    when (result) {
                        is Resource.Loading -> {
                            showProgress()
                        }
                        is Resource.Success -> {
                            hideProgress()
                            if(result.data) {
                                toast("Usuario registrado con éxito")
                                navigateFromSignUpToSignIn()
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

    private fun navigateFromSignUpToSignIn(){
        clearFields()
        requireActivity().onBackPressed()
        val builder = NavOptions.Builder()
            .setEnterAnim(android.R.anim.fade_in)
            .setExitAnim(android.R.anim.fade_out)
            .setPopEnterAnim(R.anim.from_left)
            .setPopExitAnim(R.anim.to_right)
            .build()
        findNavController().navigate(R.id.action_mainFragment_to_signInFragment,null,builder)
    }

    private fun showProgress(){
        with(binding){
            btnRegisterSignUp.visibility = View.GONE
            progressBarSignUp.visibility = View.VISIBLE
        }
    }

    private fun hideProgress(){
        with(binding){
            btnRegisterSignUp.visibility = View.VISIBLE
            progressBarSignUp.visibility = View.GONE
        }
    }

    private fun clearFields(){
        with(binding){
            etUsernameSignUp.text?.clear()
            etPhoneSignUp.text?.clear()
            etPasswordSignUp.text?.clear()
            etEmailSignUp.text?.clear()
            cetUsernameSignUp.clearFocus()
            cetPhoneSignUp.clearFocus()
            cetEmailSignUp.clearFocus()
            cetPasswordSignUp.clearFocus()
        }
    }

}