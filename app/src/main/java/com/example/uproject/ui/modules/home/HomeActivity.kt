package com.example.uproject.ui.modules.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.uproject.R
import com.example.uproject.common.utils.setNavigationBarColor
import com.example.uproject.core.preferences

import com.example.uproject.databinding.ActivityHomeBinding
import com.example.uproject.databinding.NavHeaderBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding            : ActivityHomeBinding
    private lateinit var drawerLayout       : DrawerLayout
    private lateinit var navigationView     : NavigationView
    private lateinit var navController      : NavController
    private lateinit var bottomNavigation   : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationBarColor(this)

        drawerLayout        = binding.drawerLayout
        navigationView      = binding.navigationView
        bottomNavigation    = binding.bottomNavigation

        configBottomNavigation()
        setupNavigation()
    }

    private fun configBottomNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)
        navigationView.setupWithNavController(navController)

        //Set tint of tab icons
        bottomNavigation.itemIconTintList = null
    }

    private fun setupNavigation(){
        val imageMenu = binding.imageMenu
        imageMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        //Config destinations of activities
//        navigationView.setNavigationItemSelectedListener { menuItem->
//            when (menuItem.itemId) {
//                R.id.navigation_home ->{
//                    navController.navigate(R.id.navigation_home)
//                    closeDrawer()
//                }
//
//                R.id.navigation_favorite ->{
//                    navController.navigate(R.id.navigation_favorite)
//                    closeDrawer()
//                }
//
//                R.id.navigation_bag ->{
//                    navController.navigate(R.id.navigation_bag)
//                    closeDrawer()
//                }
//
//                R.id.navigation_profile ->{
//                    navController.navigate(R.id.navigation_profile)
//                    closeDrawer()
//                }

//                R.id.navigation_contact_us ->{
//                    val intent = Intent(this, ContactUsActivity::class.java)
//                    startActivity(intent)
//                    closeDrawer()
//                }
//
//                R.id.navigation_about_us ->{
//                    val intent = Intent(this, AboutUsActivity::class.java)
//                    startActivity(intent)
//                    closeDrawer()
//                }
//
//                R.id.sign_out -> {
//                    val auth = FirebaseAuth.getInstance()
//                    auth.signOut()
//                    preferences.clear()
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//            true
//        }

        val headerView = navigationView.getHeaderView(0)
        val headerBinding = NavHeaderBinding.bind(headerView)

        headerBinding.headerUsername.text = getCustomName(preferences.username.toString())
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            Handler(Looper.getMainLooper()).postDelayed({
                drawerLayout.closeDrawer(GravityCompat.START)
            }, 200)
        } else super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
//        navigationView.setCheckedItem(R.id.navigation_home)
//        bottomNavigation.selectedItemId = R.id.navigation_home
    }

    private fun closeDrawer() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START)
        }, 200)

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