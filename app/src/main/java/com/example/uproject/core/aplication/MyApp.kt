package com.example.uproject.core.aplication

import android.app.Application
import com.example.uproject.common.MySharePreferences

val preferences: MySharePreferences by lazy { MyApp.prefs!! }

class MyApp: Application() {

    companion object{
        var prefs: MySharePreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = MySharePreferences(applicationContext)
    }
}