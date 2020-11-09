package com.example.uproject.ui.fragments.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uproject.R
import com.example.uproject.ui.fragments.recoverpassword.RecoverPasswordFragment
import com.example.uproject.utils.isEnabledasd
import com.example.uproject.utils.setNavigationBarColor
import com.example.uproject.utils.setStatusBarColor
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor(R.color.color_Uranian_Blue)
        setNavigationBarColor(R.color.color_Unbleached_Silk)

        lbl_forgot_password.setOnClickListener {
            val customDialog = RecoverPasswordFragment()
            customDialog.show(requireActivity().supportFragmentManager,"RecoverPassword")
        }

    }

}