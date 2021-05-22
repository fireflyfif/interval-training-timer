package com.example.intervaltrainingtimer

import android.os.*
import androidx.appcompat.app.*
import androidx.navigation.fragment.*
import com.example.intervaltrainingtimer.databinding.*
import dagger.hilt.android.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return
    }
}