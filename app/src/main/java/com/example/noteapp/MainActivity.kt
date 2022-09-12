package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var hostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavController()
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setUpNavController() {
        hostFragment =
            supportFragmentManager.findFragmentById(R.id.frameContainer) as NavHostFragment
        navController = hostFragment.navController
    }
}