package com.example.uproject.utils

import android.os.Build
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.uproject.R

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