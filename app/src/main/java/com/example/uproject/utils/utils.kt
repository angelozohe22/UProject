package com.example.uproject.utils

import android.graphics.Color
import android.os.Build
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.setStatusBarColor(color: Int = Color.WHITE) {
    val window = this.requireActivity().window
    val customColor = transformColor(color)
    if (Build.VERSION.SDK_INT >= 21) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = customColor //Define color
    }
}

fun Fragment.setNavigationBarColor(color: Int = Color.WHITE) {
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