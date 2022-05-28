package com.example.uproject.ui.login.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.uproject.R
import com.example.uproject.core.Resource
import com.example.uproject.databinding.FragmentSignUpBinding
import com.example.uproject.common.utils.*
import com.example.uproject.ui.login.AuthViewModel
import com.example.uproject.ui.login.MainActivity

class SignUpFragment : Fragment() { //BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up)

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        viewModel = (activity as MainActivity).viewModel
        setStatusBarColor(requireActivity())
        setNavigationBarColor(requireActivity())

        binding.apply{
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun textFieldSignUpValidations(){
        val btnSignUp  = binding.btnRegisterSignUp
        val cetPhone     = binding.cetPhoneSignUp
        val cetEmail     = binding.cetEmailSignUp
        val cetPassword  = binding.cetPasswordSignUp
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
            viewLifecycleOwner
        ){
            it?.let { result ->
                when (result) {
                    is Resource.Loading -> {
                        showProgress()
                    }
                    is Resource.Success -> {
                        hideProgress()
                        if(result.data) {
                            toast("Usuario registrado con éxito. Verificar correo para iniciar sesión")
                            navigateFromSignUpToSignIn()
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
        binding.apply{
            btnRegisterSignUp.visibility = View.GONE
            progressBarSignUp.visibility = View.VISIBLE
        }
    }

    private fun hideProgress(){
        binding.apply{
            btnRegisterSignUp.visibility = View.VISIBLE
            progressBarSignUp.visibility = View.GONE
        }
    }

    private fun clearFields(){
        binding.apply{
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}