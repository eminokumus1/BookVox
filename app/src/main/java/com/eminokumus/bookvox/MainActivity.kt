package com.eminokumus.bookvox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.eminokumus.bookvox.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.myNavHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottombar.setupWithNavController(navController)
        bottomNavItemChangeListener(binding.bottombar, navController)
    }

    private fun bottomNavItemChangeListener(
        bottombar: BottomNavigationView,
        navController: NavController
    ) {
        bottombar.setOnItemSelectedListener { item ->
            if (item.itemId != bottombar.selectedItemId) {
                navController.popBackStack(bottombar.selectedItemId, false
                    , saveState = false)
                navController.navigate(item.itemId)
            } else if (item.itemId == bottombar.selectedItemId) {
                navController.popBackStack(item.itemId, false)
                navController.navigate(item.itemId)
            }
            true
        }
    }
}