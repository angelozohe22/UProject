package com.example.uproject.common

import android.content.Context

class MySharePreferences(context: Context) {

    private val fileName = "dulcekat-preferences"
    private val prefs = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    var deviceToken: String?
        get() = prefs.getString("device_token","")
        set(value) = prefs.edit().putString("device_token", value).apply()

    var username: String?
        get() = prefs.getString("username","")
        set(value) = prefs.edit().putString("username", value).apply()

    var phone: String?
        get() = prefs.getString("phone","")
        set(value) = prefs.edit().putString("phone", value).apply()

    var email: String?
        get() = prefs.getString("email","")
        set(value) = prefs.edit().putString("email", value).apply()

    fun clear(){
        prefs.edit().remove("device_token").apply()
        prefs.edit().remove("username").apply()
        prefs.edit().remove("phone").apply()
        prefs.edit().remove("email").apply()
    }

}