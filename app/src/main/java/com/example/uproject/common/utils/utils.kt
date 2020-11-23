package com.example.uproject.common.utils

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.uproject.R
import com.example.uproject.core.aplication.Constants.EMAIL_ALREADY_IN_USE
import com.example.uproject.core.aplication.Constants.INVALID_EMAIL
import com.example.uproject.core.aplication.Constants.MISSING_EMAIL
import com.example.uproject.core.aplication.Constants.USER_DISABLED
import com.example.uproject.core.aplication.Constants.USER_NOT_FOUND
import com.example.uproject.core.aplication.Constants.WRONG_PASSWORD
import com.google.firebase.auth.FirebaseAuthException
import java.lang.Exception

fun Fragment.setStatusBarColor(color: Int = R.color.color_Uranian_Blue) {
    val window = this.requireActivity().window
    val customColor = transformColor(color)
    if (Build.VERSION.SDK_INT >= 21) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = customColor //Define color
    }
}

fun Fragment.setNavigationBarColor(color: Int = R.color.color_Unbleached_Silk) {
    val window = this.requireActivity().window
    val customColor = transformColor(color)
    if (Build.VERSION.SDK_INT >= 21) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.navigationBarColor = customColor //Define color
    }
}

fun Fragment.transformColor(color: Int): Int{
    return ContextCompat.getColor(requireContext(), color)
}

fun afterTextChanged(function: (s: Editable) -> Unit): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        override fun afterTextChanged(s: Editable) {
            function(s)
        }
    }
}

fun String.getColoredSpanned(color: String): String{
    return "<font color=$color>$this</font>"
}

fun isNullOrEmpty(text: Any): Boolean{
    return when(text){
        is String -> TextUtils.isEmpty(text)
        else -> false
    }
}

fun isEmailValid(email: String): Boolean{
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}

fun Fragment.authErrorMessage(error: Exception): String{
    return when(error){
        is FirebaseAuthException ->{
            when(error.errorCode){
                USER_NOT_FOUND -> getString(R.string.error_user_not_found)
                USER_DISABLED  -> getString(R.string.error_user_disable)
                WRONG_PASSWORD -> getString(R.string.error_wrong_password)
                INVALID_EMAIL  -> getString(R.string.error_invalid_email)
                MISSING_EMAIL  -> getString(R.string.error_missing_email)
                EMAIL_ALREADY_IN_USE -> getString(R.string.error_email_already_in_use)
                else -> error.message.toString()
            }
        }
        else -> error.message.toString()
    }
}

fun Fragment.toast(message: String){
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_LONG).show()
}