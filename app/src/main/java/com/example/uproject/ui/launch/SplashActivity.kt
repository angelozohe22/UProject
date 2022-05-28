package com.example.uproject.ui.launch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uproject.common.FirebaseAuth
import com.example.uproject.ui.modules.home.HomeActivity
import com.example.uproject.ui.login.MainActivity

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
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}