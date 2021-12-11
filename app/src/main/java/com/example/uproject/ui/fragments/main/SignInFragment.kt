package com.example.uproject.ui.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.uproject.R
import com.example.uproject.base.BaseFragment
import com.example.uproject.core.Resource
import com.example.uproject.data.firebase.auth.FirebaseAuthDataSourceImpl
import com.example.uproject.databinding.FragmentSignInBinding
import com.example.uproject.domain.auth.AuthRepositoryImpl
import com.example.uproject.ui.viewmodels.auth.SignInViewModel
import com.example.uproject.ui.viewmodels.auth.factory.AuthViewModelFactory
import com.example.uproject.common.utils.*
import com.example.uproject.ui.activities.home.HomeActivity

class SignInFragment : Fragment() { //BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in)

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel by activityViewModels<SignInViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                FirebaseAuthDataSourceImpl()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater, container, false)

        setStatusBarColor(requireActivity())
        setNavigationBarColor(requireActivity())

        binding.btnLoginSignIn.apply{
            isEnabled  = false
            setBackgroundResource(R.drawable.btn_corner_dissable)
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
            val lblQuestion = getString(R.string.lbl_from_logIn_to_question).

            getColoredSpanned(getString(R.string.color_Gray_Web))
            val lblAnswer   = getString(R.string.lbl_from_logIn_to_answer).getColoredSpanned(getString(R.string.color_Brown_Sugar))
            text = HtmlCompat.fromHtml("$lblQuestion $lblAnswer",HtmlCompat.FROM_HTML_MODE_LEGACY)

            setOnClickListener {
                navigateFromSignInToSignUp()
            }
        }
        textFieldsSignInValidations()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                            val intent = Intent(requireActivity(), HomeActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
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
        binding.apply{
            btnLoginSignIn.visibility = View.GONE
            progressBarSignIn.visibility = View.VISIBLE
        }
    }

    private fun hideProgress(){
        binding.apply{
            btnLoginSignIn.visibility = View.VISIBLE
            progressBarSignIn.visibility = View.GONE
        }
    }

    private fun clearFields(){
        binding.apply{
            etEmailSignIn.text?.clear()
            etPasswordSignIn.text?.clear()
            cetEmailSignIn.clearFocus()
            cetPasswordSignIn.clearFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}