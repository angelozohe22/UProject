package com.example.uproject.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uproject.common.FirebaseAuth
import com.example.uproject.ui.activities.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goTo()
    }

    private fun goTo(){
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser == null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, MainActivity::class.java) // Esto tiene que cambiar a home
            startActivity(intent)
            finish()
        }
    }
}