package com.example.uproject.ui.modules.contactus

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.widget.Toast
import com.example.uproject.R
import com.example.uproject.common.utils.afterTextChanged
import com.example.uproject.common.utils.isNullOrEmpty
import com.example.uproject.databinding.ActivityContactUsBinding
import java.util.*

class ContactUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendMessageContactUs.apply{
            isEnabled  = false
            setBackgroundResource(R.drawable.btn_corner_dissable)
        }

        binding.btnBackContactUs.setOnClickListener {
            onBackPressed()
        }

        textFieldMessageValidation()

    }

    private fun textFieldMessageValidation(){
        val btnSendMessageContactUs = binding.btnSendMessageContactUs
        val cetMessageContactus = binding.cetMessageContactus
        val etMessageContactus = binding.etMessageContactus

        val validate = afterTextChanged {
            val message = etMessageContactus.text.toString().trim()

            if(isNullOrEmpty(message)){
                cetMessageContactus.error = "Ingrese un mensaje"
            }else cetMessageContactus.error = null

            btnSendMessageContactUs.apply {
                isEnabled = !isNullOrEmpty(message)
                if(isEnabled) setBackgroundResource(R.drawable.btn_corner)
                else setBackgroundResource(R.drawable.btn_corner_dissable)
                setOnClickListener {
                    val phoneContact = "965435157"
                    //Elegimos un número en específico para enviar el mensaje
                    val uri = "whatsapp://send?phone=51${PhoneNumberUtils.stripSeparators(phoneContact)}&text=$message"
                    if(isWhatsAppInstalled("com.whatsapp")){
                        val intent  = Intent().apply {
                            action  = Intent.ACTION_VIEW
                            data    = Uri.parse(uri)
                        }
                        startActivity(intent)
                        Toast.makeText(applicationContext, "Enviando mensaje", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(applicationContext, "WhatsApp no instalado", Toast.LENGTH_SHORT).show()
//                    val uriMarket = Uri.parse("http:/market://details?id=com.whatsapp")
//                    val intentGoToMarket = Intent(Intent.ACTION_VIEW, uriMarket)
//                    startActivity(intentGoToMarket)
                    }
                }
            }
        }

        etMessageContactus.addTextChangedListener(validate)

    }

    private fun isWhatsAppInstalled(uri: String): Boolean {
        val packageManager = Objects.requireNonNull(applicationContext.packageManager)
        return try{
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        }catch (e: PackageManager.NameNotFoundException){
            false
        }
    }

}