package com.example.uproject.ui.login.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.uproject.R
import com.example.uproject.common.FacebookLoginManager
import com.example.uproject.common.utils.*
import com.example.uproject.core.Resource
import com.example.uproject.databinding.FragmentSignInBinding
import com.example.uproject.ui.login.AuthViewModel
import com.example.uproject.ui.login.welcome.WelcomeActivity
import com.example.uproject.ui.modules.home.HomeActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider

class SignInFragment : Fragment() { //BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in)

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: AuthViewModel

    //For google login
    private val googleRegisterResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        result?.let {
            if (result.resultCode == Activity.RESULT_OK){
                val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).result
                account?.let {
                    googleAuthForFirebase(it)
                }
            }
        }
    }

    //For facebook Login
    var callbackManager = CallbackManager.Factory.create()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater, container, false)

        viewModel = (activity as WelcomeActivity).viewModel
        setStatusBarColor(requireActivity())
        setNavigationBarColor(requireActivity())

        registerCallbackForFacebookLogin()

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

        binding.btnLoginGoogle.setOnClickListener {
            setupGoogleAuth()
        }

        binding.btnLoginFacebook.setOnClickListener {
            setupFacebookAuth()
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

    private fun setupFacebookAuth() {
        FacebookLoginManager.getInstance().apply {
            logInWithReadPermissions(this@SignInFragment, listOf("public_profile","email"))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //For facebook login
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerCallbackForFacebookLogin(){
        FacebookLoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                facebookAuthForFirebase(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.e("FB_TAG", "-->> holaaaaaaaa entro cancel")
            }

            override fun onError(error: FacebookException) {
                Log.e("FB_TAG", "-->> holaaaaaaaa entro error: ${error.localizedMessage}")
            }
        })
    }

    private fun setupGoogleAuth(){
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webclient_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), options)
        googleSignInClient.revokeAccess()
        googleSignInClient.signOut()
        googleSignInClient.signInIntent.also {
            googleRegisterResult.launch(it)
        }
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
                    hideKeyboard()
                    setupSignInObserver(email, password)
                }
            }
        }
        etEmail.addTextChangedListener(validate)
        etPassword.addTextChangedListener(validate)
    }

    private fun setupSignInObserver(email: String, password: String){
        viewModel.signIn(email, password).observe(
            viewLifecycleOwner
        ){
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
                        toast(result.errorMessage)
                    }
                }
            }
        }
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        viewModel.signInWithGoogle(credentials).observe(
            viewLifecycleOwner
        ){
            it?.let { result ->
                when (result){
                    Resource.Loading -> {
//                        showProgress()
                    }
                    is Resource.Success -> {
//                        hideProgress()
                        val intent = Intent(requireActivity(), HomeActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    is Resource.Failure -> {
//                        hideProgress()
                        toast(result.errorMessage)
                    }
                }
            }
        }
    }

    private fun facebookAuthForFirebase(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        viewModel.signInWithFacebook(credential).observe(
            viewLifecycleOwner
        ){
            it?.let { result ->
                when (result){
                    Resource.Loading -> {
//                        showProgress()
                    }
                    is Resource.Success -> {
//                        hideProgress()
                        val intent = Intent(requireActivity(), HomeActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    is Resource.Failure -> {
//                        hideProgress()
                        toast(result.errorMessage)
                    }
                }
            }
        }
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
            btnLoginSignIn.visibility = View.INVISIBLE
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