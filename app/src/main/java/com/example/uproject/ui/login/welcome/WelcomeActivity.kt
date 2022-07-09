package com.example.uproject.ui.login.welcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.uproject.databinding.ActivityMainBinding
import com.example.uproject.data.repository.AuthRepositoryImpl
import com.example.uproject.data.source.Remote.auth.RemoteAuthSource
import com.example.uproject.ui.login.AuthViewModel
import com.example.uproject.ui.login.AuthViewModelFactory

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                RemoteAuthSource()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}