package com.example.uproject.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.uproject.R
import com.example.uproject.common.Constants.EMAIL_ALREADY_IN_USE
import com.example.uproject.common.Constants.INVALID_EMAIL
import com.example.uproject.common.Constants.MISSING_EMAIL
import com.example.uproject.common.Constants.USER_DISABLED
import com.example.uproject.common.Constants.USER_NOT_FOUND
import com.example.uproject.common.Constants.WRONG_PASSWORD
import com.example.uproject.core.ctx
import com.google.firebase.auth.FirebaseAuthException
import java.lang.Exception

fun setStatusBarColor(activity: Activity, color: Int = ContextCompat.getColor(ctx, R.color.color_Uranian_Blue)) {
    val window = activity.window
    val hsv = FloatArray(3)
    var customColor: Int = color

    Color.colorToHSV(customColor, hsv)
    customColor = Color.HSVToColor(hsv)

    if (Build.VERSION.SDK_INT >= 21) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = customColor //Define color
    }
}

fun setNavigationBarColor(activity: Activity, color: Int = ContextCompat.getColor(ctx, R.color.color_Unbleached_Silk)) {
    val window = activity.window
    val hsv = FloatArray(3)
    var customColor: Int = color

    Color.colorToHSV(customColor, hsv)
    customColor = Color.HSVToColor(hsv)

    if (Build.VERSION.SDK_INT >= 21) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.navigationBarColor = customColor //Define color
    }
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

fun getErrorMessage(error: Exception): String{
    return when(error){
        is FirebaseAuthException ->{
            when(error.errorCode){
                USER_NOT_FOUND -> "La dirección de correo electronico no ha sido registrado"
                USER_DISABLED  -> "Esta cuenta se encuentra temporalmente deshabilitado"
                WRONG_PASSWORD -> "Contraseña incorrecta, vuelva a intentar"
                INVALID_EMAIL  -> "La dirección de correo electrónico está mal formateada"
                MISSING_EMAIL  -> "Se debe proporcionar una dirección de correo electrónico"
                EMAIL_ALREADY_IN_USE -> "La dirección de correo electrónico ya ha sido registrado"
                else -> "Ha ocurrido un error"
            }
        }
        else -> "Ha ocurrido un error"
    }
}



fun Fragment.toast(message: String){
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_LONG).show()
}

fun EditText.makeClearableEditText() {

    val clearDrawable = ContextCompat.getDrawable(this.context, R.drawable.ic_cancel)
    clearDrawable?.setBounds(0,0, clearDrawable.intrinsicWidth, clearDrawable.intrinsicHeight)

    val validation = afterTextChanged {
        this.setCompoundDrawables(null, null,
            if (text.isNotEmpty()) clearDrawable else null,
            null)
    }
    this.addTextChangedListener(validation)

    this.onRightDrawableClicked {
        this.text.clear()
        this.setCompoundDrawables(null, null, null, null)
        this.requestFocus()
    }
}

//Revisar
@SuppressLint("ClickableViewAccessibility")
fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is EditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}

fun Fragment.hideKeyboardDialog(){
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
}