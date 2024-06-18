package com.c241ps220.centingapps.views.SplashScreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.c241ps220.centingapps.MainActivity
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.databinding.ActivitySplashScreenBinding
import com.c241ps220.centingapps.views.History.HistoryActivityViewModel
import com.c241ps220.centingapps.views.Onboarding.OnBoardingActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var splashscreenViewModel: SplashscreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        with(binding) {

            splashscreenViewModel = obtainViewModel(this@SplashScreenActivity)


            lifecycleScope.launch {
                delay(2000L)
                withContext(Dispatchers.Main) {
                    splashscreenViewModel.getSession().observe(this@SplashScreenActivity) { user ->
                        if (!user.isLogin) {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    OnBoardingActivity::class.java
                                )
                            )
                            finish()
                        } else {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    MainActivity::class.java
                                )
                            )
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): SplashscreenViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, UserPreference.getInstance(application))
        return ViewModelProvider(activity, factory).get(SplashscreenViewModel::class.java)
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