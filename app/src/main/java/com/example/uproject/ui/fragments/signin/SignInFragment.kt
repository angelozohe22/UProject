package com.example.uproject.ui.fragments.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.uproject.R
import com.example.uproject.base.BaseFragment
import com.example.uproject.core.Resource
import com.example.uproject.data.firebase.auth.FirebaseDataSourceImpl
import com.example.uproject.databinding.FragmentSignInBinding
import com.example.uproject.domain.auth.AuthRepositoryImpl
import com.example.uproject.ui.fragments.restorepassword.RecoverPasswordFragment
import com.example.uproject.ui.viewmodels.SignInViewModel
import com.example.uproject.ui.viewmodels.factory.AuthViewModelFactory
import com.example.uproject.common.utils.*

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    
    private val viewModel by activityViewModels<SignInViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                FirebaseDataSourceImpl()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)
        setStatusBarColor()
        setNavigationBarColor()

        with(binding){
            btnLoginSignIn.apply {
                isEnabled  = false
                setBackgroundResource(R.drawable.btn_corner_dissable)
            }
        }

        binding.lblForgotPassword.setOnClickListener {
            val customDialog = RecoverPasswordFragment()
            customDialog.show(requireActivity().supportFragmentManager,"RecoverPassword")
        }

        binding.btnBackSignIn.setOnClickListener {
            clearFields()
            requireActivity().onBackPressed()
        }

        binding.lblFromSignInTo.apply{
            val lblQuestion = getString(R.string.lbl_from_logIn_to_question).getColoredSpanned(getString(R.string.color_Gray_Web))
            val lblAnswer   = getString(R.string.lbl_from_logIn_to_answer).getColoredSpanned(getString(R.string.color_Brown_Sugar))
            text = HtmlCompat.fromHtml("$lblQuestion $lblAnswer",HtmlCompat.FROM_HTML_MODE_LEGACY)

            setOnClickListener {
                navigateFromSignInToSignUp()
            }
        }
        textFieldsSignInValidations()
    }

    private fun textFieldsSignInValidations(){
        val btnSignIn   = binding.btnLoginSignIn
        val cetEmail    = binding.cetEmailSignIn
        val etEmail     = binding.etEmailSignIn
        val cetPassword = binding.cetPasswordSignIn
        val etPassword  = binding.etPasswordSignIn

        val validate = afterTextChanged {
            val email    = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

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

            btnSignIn.apply {
                isEnabled = !isNullOrEmpty(email)
                        &&  !isNullOrEmpty(password)
                        &&   isEmailValid(email)
                        &&   password.length > 6
                if(isEnabled) setBackgroundResource(R.drawable.btn_corner)
                else setBackgroundResource(R.drawable.btn_corner_dissable)

                setOnClickListener {
                    setupSignInObserver(email, password)
                }
            }
        }
        etEmail.addTextChangedListener(validate)
        etPassword.addTextChangedListener(validate)
    }

    private fun setupSignInObserver(email: String, password: String){
        viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Resource.Loading -> {
                        showProgress()
                    }
                    is Resource.Success -> {
                        hideProgress()
                        if (result.data) {
                            //Enviar a la pantalla de iniciar sesión
                            toast("Logeado")
                            clearFields()
                        }else toast("Debe verificar el correo electrónico para poder iniciar sesión")
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

    private fun navigateFromSignInToSignUp(){
        clearFields()
        requireActivity().onBackPressed()
        val builder = NavOptions.Builder()
            .setEnterAnim(android.R.anim.fade_in)
            .setExitAnim(android.R.anim.fade_out)
            .setPopEnterAnim(R.anim.from_right)
            .setPopExitAnim(R.anim.to_left)
            .build()
        findNavController().navigate(R.id.action_mainFragment_to_signUpFragment,null,builder)
    }

    private fun showProgress(){
        with(binding){
            btnLoginSignIn.visibility = View.GONE
            progressBarSignIn.visibility = View.VISIBLE
        }
    }

    private fun hideProgress(){
        with(binding){
            btnLoginSignIn.visibility = View.VISIBLE
            progressBarSignIn.visibility = View.GONE
        }
    }

    private fun clearFields(){
        with(binding){
            etEmailSignIn.text?.clear()
            etPasswordSignIn.text?.clear()
            cetEmailSignIn.clearFocus()
            cetPasswordSignIn.clearFocus()

        }
    }

}