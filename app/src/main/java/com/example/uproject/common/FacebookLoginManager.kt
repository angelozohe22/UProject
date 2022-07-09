package com.example.uproject.common

import com.facebook.login.LoginManager


class FacebookLoginManager private constructor() {

    companion object{
        @Volatile
        private var instance: LoginManager? = null
        fun getInstance(): LoginManager =
            instance ?: synchronized(this){
                instance ?: LoginManager.getInstance().also { instance = it }
            }
    }

}