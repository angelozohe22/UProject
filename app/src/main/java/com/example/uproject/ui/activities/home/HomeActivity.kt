package com.example.uproject.ui.activities.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.uproject.R
import com.example.uproject.common.FirebaseAuth
import com.example.uproject.common.utils.setNavigationBarColor
import com.example.uproject.core.aplication.preferences
import com.example.uproject.data.Remote.home.RemoteFirestoreDataSourceImpl
import com.example.uproject.data.local.db.DulcekatDataBase
import com.example.uproject.data.local.source.LocalDataSourceImpl
import com.example.uproject.databinding.ActivityHomeBinding
import com.example.uproject.databinding.NavHeaderBinding
import com.example.uproject.domain.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.activities.home.aboutus.AboutUsActivity
import com.example.uproject.ui.activities.home.contactus.ContactUsActivity
import com.example.uproject.ui.activities.main.MainActivity
import com.example.uproject.ui.viewmodels.home.HomeViewModel
import com.example.uproject.ui.viewmodels.home.factoryhome.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding            : ActivityHomeBinding
    private lateinit var drawerLayout       : DrawerLayout
    private lateinit var navigationView     : NavigationView
    private lateinit var bottomNavigation   : BottomNavigationView
    private lateinit var navController      : NavController

    private val viewModel by viewModels<HomeViewModel>{
        HomeViewModelFactory(
            DulcekatRepositoryImpl(
                LocalDataSourceImpl(DulcekatDataBase.getInstance(this)),
                RemoteFirestoreDataSourceImpl()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var flag = preferences.flagGetDates

        if(flag == 0){
            Log.e("paso", "una vez")
            viewModel.getListProductFirebase()
            viewModel.getListCategoryFirebase()
            preferences.flagGetDates = 1
        }
        

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationBarColor(this)

        drawerLayout        = binding.drawerLayout
        navigationView      = binding.navigationView
        bottomNavigation    = binding.bottomNavigation
        navController       = findNavController(R.id.nav_host_fragment)

        setupNavigation()
    }

    private fun setupNavigation(){
        val imageMenu = binding.imageMenu
        imageMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        NavigationUI.setupWithNavController(navigationView, navController)
        bottomNavigation.setupWithNavController(navController)
        bottomNavigation.itemIconTintList = null
 
        navigationView.setNavigationItemSelectedListener { menuItem->
            when (menuItem.itemId) {
                R.id.navigation_home ->{
                    navController.navigate(R.id.navigation_home)
                    closeDrawer()
                }

                R.id.navigation_favorite ->{
                    navController.navigate(R.id.navigation_favorite)
                    closeDrawer()
                }

                R.id.navigation_bag ->{
                    navController.navigate(R.id.navigation_bag)
                    closeDrawer()
                }

                R.id.navigation_profile ->{
                    navController.navigate(R.id.navigation_profile)
                    closeDrawer()
                }

                R.id.navigation_contact_us ->{
                    val intent = Intent(this, ContactUsActivity::class.java)
                    startActivity(intent)
                    closeDrawer()
                }

                R.id.navigation_about_us ->{
                    val intent = Intent(this, AboutUsActivity::class.java)
                    startActivity(intent)
                    closeDrawer()
                }

                R.id.sign_out -> {
                    val auth = FirebaseAuth.getInstance()
                    auth.signOut()
                    preferences.clear()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

        val headerView = navigationView.getHeaderView(0)
        val headerBinding = NavHeaderBinding.bind(headerView)

        headerBinding.headerUsername.text = getCustomName(preferences.username.toString())
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        navigationView.setCheckedItem(R.id.navigation_home)
        bottomNavigation.selectedItemId = R.id.navigation_home
    }

    private fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun getCustomName(name: String): String{
        val nameList = name.split(" ")
        var mysc: String
        var mnsc: String

        val newNameList = mutableListOf<String>()
        for(text in nameList){
            mysc = text.substring(0,1).capitalize()
            mnsc = text.substring(1,text.length).toLowerCase()
            newNameList.add(mysc.plus(mnsc))
        }

        val first  = newNameList[0]
        val realName: String
        realName = if(newNameList.size > 1){
            val second = newNameList[1].substring(0,1).plus(".")
            "$first $second"
        }else first

        return realName
    }

}