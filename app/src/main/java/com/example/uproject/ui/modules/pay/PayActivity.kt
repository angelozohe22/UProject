package com.example.uproject.ui.modules.pay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.uproject.R
import com.example.uproject.common.utils.setNavigationBarColor
import com.example.uproject.core.preferences
import com.example.uproject.data.source.Remote.home.RemoteFirestoreDataSourceImpl
import com.example.uproject.data.source.helpers.DulcekatDataBase
import com.example.uproject.data.source.local.LocalDataSourceImpl
import com.example.uproject.databinding.ActivityPayBinding
import com.example.uproject.data.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.modules.home.HomeViewModelFactory

class PayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayBinding
    var price = 0.0

    private val viewModel by viewModels<PayViewModel>{
        HomeViewModelFactory(
            DulcekatRepositoryImpl(
                LocalDataSourceImpl(DulcekatDataBase.getInstance(this)),
                RemoteFirestoreDataSourceImpl()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigationBarColor(this, ContextCompat.getColor(this, R.color.color_Uranian_Blue))

        if(intent != null){
            price = intent.getDoubleExtra("totalprice", 0.0)
        }

        viewModel.deleteAllOrdersByProducts()
        preferences.flagOrder = 0

        binding.tvNameUser.text = preferences.username
        binding.tvTotalPricePay.text = "S/ ".plus(price)
        binding.btnBackPay.setOnClickListener {
            onBackPressed()
        }

    }
}