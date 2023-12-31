package com.development.gocipes.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.development.gocipes.auth.presentation.AuthActivity
import com.development.gocipes.databinding.ActivityRoutingBinding
import com.development.gocipes.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoutingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoutingBinding
    private val viewModel by viewModels<RoutingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                delay(2000L)
                viewModel.isLoggedIn().observe(this@RoutingActivity) { isLogin ->
                    moveToMain(isLogin)
                }
            }
        }
    }

    private fun moveToMain(isLogin: Boolean) {
        if (isLogin) {
            startActivity(Intent(this@RoutingActivity, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@RoutingActivity, AuthActivity::class.java))
            finish()
        }
    }
}