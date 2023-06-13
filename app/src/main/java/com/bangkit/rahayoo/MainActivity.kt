package com.bangkit.rahayoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bangkit.rahayoo.databinding.ActivityMainBinding
import com.bangkit.rahayoo.di.Injection
import com.bangkit.rahayoo.ui.home.HomeFragmentDirections

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavBar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.loginFragment -> {
                    binding.bottomNavBar.visibility = View.GONE
                }
                R.id.registerFragment -> {
                    binding.bottomNavBar.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavBar.visibility = View.VISIBLE
                }
            }
        }
    }
}