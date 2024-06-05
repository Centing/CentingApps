package com.c241ps220.centingapps.views.SplashScreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivitySplashScreenBinding
import com.c241ps220.centingapps.views.WelcomeScreen.WelcomingActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        with(binding) {

            val fadeIn = AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.fade_in)

            imageView.startAnimation(fadeIn)

            lifecycleScope.launch {
                delay(2000L)
                withContext(Dispatchers.Main) {
//                    viewModel.getSession().observe(this@SplashscreenActivity) { user ->
//                        if (!user.isLogin) {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    WelcomingActivity::class.java
                                )
                            )
                            finish()
//                        } else {
//                            startActivity(
//                                Intent(
//                                    this@SplashscreenActivity,
//                                    MainActivity::class.java
//                                )
//                            )
//                            finish()
//                        }
//                    }
                }
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    companion object {
        const val DELAY_DURATION_MS = 2000L
    }
}