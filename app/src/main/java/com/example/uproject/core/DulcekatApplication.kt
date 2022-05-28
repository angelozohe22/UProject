package com.example.uproject.core

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.uproject.common.MySharePreferences

val preferences     : MySharePreferences by lazy { DulcekatApplication.prefs!! }
lateinit var ctx    : Context

class DulcekatApplication: Application() {

    companion object{
        var prefs: MySharePreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        prefs = MySharePreferences(applicationContext)
        ctx = this
    }
}