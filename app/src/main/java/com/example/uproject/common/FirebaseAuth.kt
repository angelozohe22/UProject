package com.example.uproject.common

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuth private constructor() {

    companion object{
        @Volatile
        private var instance: FirebaseAuth? = null
        fun getInstance(): FirebaseAuth =
            instance ?: synchronized(this){
                instance ?: FirebaseAuth.getInstance().also { instance = it }
            }
    }

}