package com.example.uproject.ui.activities.home.pay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.uproject.R
import com.example.uproject.common.utils.setNavigationBarColor
import com.example.uproject.core.aplication.preferences
import com.example.uproject.data.firebase.home.FirebaseFirestoreDataSourceImpl
import com.example.uproject.data.local.db.DulcekatDataBase
import com.example.uproject.data.local.source.LocalDataSourceImpl
import com.example.uproject.databinding.ActivityPayBinding
import com.example.uproject.domain.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.viewmodels.home.PayViewModel
import com.example.uproject.ui.viewmodels.home.ProductDetailsViewModel
import com.example.uproject.ui.viewmodels.home.factoryhome.HomeViewModelFactory

class PayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayBinding
    var price = 0.0

    private val viewModel by viewModels<PayViewModel>{
        HomeViewModelFactory(
            DulcekatRepositoryImpl(
                LocalDataSourceImpl(DulcekatDataBase.getInstance(this)),
                FirebaseFirestoreDataSourceImpl()
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