package com.example.uproject.ui.fragments.signin

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.uproject.R
import com.example.uproject.base.BaseFragment
import com.example.uproject.databinding.FragmentSignInBinding
import com.example.uproject.ui.fragments.recoverpassword.RecoverPasswordFragment
import com.example.uproject.utils.*

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

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
            requireActivity().onBackPressed()
        }

        binding.lblFromSignInTo.apply{
            val lblQuestion = getString(R.string.lbl_from_logIn_to_question).getColoredSpanned(getString(R.string.color_Gray_Web))
            val lblAnswer   = getString(R.string.lbl_from_logIn_to_answer).getColoredSpanned(getString(R.string.color_Brown_Sugar))
            text = HtmlCompat.fromHtml("$lblQuestion $lblAnswer",HtmlCompat.FROM_HTML_MODE_LEGACY)

            setOnClickListener {
                requireActivity().onBackPressed()
                val builder = NavOptions.Builder()
                    .setEnterAnim(android.R.anim.fade_in)
                    .setExitAnim(android.R.anim.fade_out)
                    .setPopEnterAnim(R.anim.from_right)
                    .setPopExitAnim(R.anim.to_left)
                    .build()
                findNavController().navigate(R.id.action_mainFragment_to_signUpFragment,null,builder)
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
            }
        }
        etEmail.addTextChangedListener(validate)
        etPassword.addTextChangedListener(validate)
    }

}