package com.example.uproject.ui.modules.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uproject.common.FacebookLoginManager
import com.example.uproject.common.FirebaseAuth
import com.example.uproject.core.preferences
import com.example.uproject.databinding.FragmentProfileBinding
import com.example.uproject.ui.login.welcome.WelcomeActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        setupProfile()

        binding.btnSignOut.setOnClickListener {

            //Implementar un viewmodel
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            signOutFacebook()
            GoogleSignIn.getClient(
                requireActivity(),
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut()
            preferences.clear()
            val intent = Intent(requireActivity(), WelcomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun signOutFacebook(){
//        AccessToken.setCurrentAccessToken(null)
        if (AccessToken.getCurrentAccessToken() == null) {
            return  // already logged out
        }

        GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/permissions/",
            null,
            HttpMethod.DELETE,
            GraphRequest.Callback {
                FacebookLoginManager.getInstance().logOut()
            }).executeAsync()
    }

    private fun setupProfile(){
        val name  = preferences.username
        val email = preferences.email
        val phone = preferences.phone
        binding.tvUsernameProfile.setText(name)
        binding.tvMailProfile.setText(email)
        binding.tvPhoneProfile.setText(phone)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}