package com.bangkit.rahayoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.bangkit.rahayoo.databinding.ActivityMainBinding
import com.bangkit.rahayoo.di.Injection

class MainActivity : AppCompatActivity() {

    private val repository = Injection.provideRepository()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isUserLoggedIn = repository.isUserLoggedIn()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (isUserLoggedIn) {
            navController.popBackStack()
            navController.navigate(getString(R.string.fragment_home_route))
        } else
            navController.navigate(getString(R.string.fragment_login_route))
    }
}