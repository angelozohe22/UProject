package com.example.uproject.ui.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.uproject.R
import com.example.uproject.base.BaseFragment
import com.example.uproject.databinding.FragmentSignInBinding
import com.example.uproject.databinding.FragmentSignUpBinding
import com.example.uproject.utils.*

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

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
                requireActivity().onBackPressed()
            }
        }

        binding.lblFromSignUpTo.apply {
            val lblQuestion = getString(R.string.lbl_from_register_to_question).getColoredSpanned(getString(R.string.color_Gray_Web))
            val lblAnswer   = getString(R.string.lbl_from_register_to_answer).getColoredSpanned(getString(R.string.color_Brown_Sugar))
            text = HtmlCompat.fromHtml("$lblQuestion $lblAnswer", HtmlCompat.FROM_HTML_MODE_LEGACY)

            setOnClickListener {
                requireActivity().onBackPressed()
                val builder = NavOptions.Builder()
                    .setEnterAnim(android.R.anim.fade_in)
                    .setExitAnim(android.R.anim.fade_out)
                    .setPopEnterAnim(R.anim.from_left)
                    .setPopExitAnim(R.anim.to_right)
                    .build()
                findNavController().navigate(R.id.action_mainFragment_to_signInFragment,null,builder)
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
        val etSurname   = binding.etSurnameSignUp
        val etPhone     = binding.etPhoneSignUp
        val etEmail     = binding.etEmailSignUp
        val etPassword  = binding.etPasswordSignUp

        val validate = afterTextChanged {
            val username = etUsername.text.toString().trim()
            val surname  = etSurname.text.toString().trim()
            val phone    = etPhone.text.toString().trim()
            val email    = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            //Only show phone error
            if(!isNullOrEmpty(phone)){
                cetPhone.error = when{
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
                        &&  !isNullOrEmpty(surname)
                        &&  !isNullOrEmpty(phone)
                        &&  phone.length == 9
                        &&  !isNullOrEmpty(email)
                        &&  isEmailValid(email)
                        &&  !isNullOrEmpty(password)
                        &&  password.length > 6
                if(isEnabled) setBackgroundResource(R.drawable.btn_corner)
                else setBackgroundResource(R.drawable.btn_corner_dissable)
            }

        }

        etUsername.addTextChangedListener(validate)
        etSurname.addTextChangedListener(validate)
        etPhone.addTextChangedListener(validate)
        etEmail.addTextChangedListener(validate)
        etPassword.addTextChangedListener(validate)

    }
}