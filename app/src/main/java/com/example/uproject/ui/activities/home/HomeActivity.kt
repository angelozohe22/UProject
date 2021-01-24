package com.example.uproject.ui.activities.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.uproject.R
import com.example.uproject.common.FirebaseAuth
import com.example.uproject.common.utils.setNavigationBarColor
import com.example.uproject.databinding.ActivityHomeBinding
import com.example.uproject.databinding.AppBarHomeBinding
import com.example.uproject.ui.activities.home.aboutus.AboutUsActivity
import com.example.uproject.ui.activities.home.contactus.ContactUsActivity
import com.example.uproject.ui.activities.main.MainActivity
import com.example.uproject.ui.fragments.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding            : ActivityHomeBinding
    private lateinit var drawerLayout       : DrawerLayout
    private lateinit var navigationView     : NavigationView
    private lateinit var bottomNavigation   : BottomNavigationView
    private lateinit var navController      : NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        //supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }

//    private fun goHome() {
//        val homeItem = bottomNavigation.menu.getItem(0).setChecked(true)
//        bottomNavigation.selectedItemId = homeItem.itemId
//        supportFragmentManager.popBackStackImmediate()
//
//        //to delete all entries from back stack immediately one by one
//        val backStackEntry = supportFragmentManager.backStackEntryCount
//        for (i in 0..backStackEntry){
//            supportFragmentManager.popBackStackImmediate()
//            Log.e("err", "paso por aqui $i")
//        }
//
//        //To navigate to the home fragment
//        val homeFragment = HomeFragment()
//        val mFragmentTransaction = supportFragmentManager.beginTransaction()
//        mFragmentTransaction.replace(R.id.nav_host_fragment, homeFragment, "Home")
//        mFragmentTransaction.commit()
//
//    }


    private fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
    }

}