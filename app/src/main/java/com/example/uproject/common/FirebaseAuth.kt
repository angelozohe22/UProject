package com.example.uproject.common

import com.example.uproject.core.aplication.Constants.FIREBASE_AUTH
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuth private constructor() {

    companion object{
        @Volatile
        private var instance: FirebaseAuth? = null
        fun getInstance(): FirebaseAuth =
            instance ?: synchronized(this){
                instance ?: FIREBASE_AUTH.also { instance = it }
            }
    }

}