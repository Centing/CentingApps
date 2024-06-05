package com.c241ps220.centingapps.views.Onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityOnBoardingBinding
import com.c241ps220.centingapps.databinding.ActivityZoomImageBinding
import com.c241ps220.centingapps.views.Login.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        with(binding) {
            viewPager.adapter = OnboardingViewPagerAdapter4(this@OnBoardingActivity, this@OnBoardingActivity)
            viewPager.offscreenPageLimit = 1

            btnBack.visibility = View.GONE

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == 2) {
                        btnNext.text = getText(R.string.finish)
                    } else {
                        btnNext.text = getText(R.string.next)
                    }

                    if (position == 0) {
                        btnBack.visibility = View.GONE
                    } else {
                        btnBack.visibility = View.VISIBLE
                    }
                }

                override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
                override fun onPageScrollStateChanged(arg0: Int) {}
            })
            TabLayoutMediator(binding.pageIndicator, viewPager) { _, _ -> }.attach()


            btnNext.setOnClickListener {
                if (getItem() > viewPager.childCount) {
                    startActivity(
                        Intent(
                            this@OnBoardingActivity,
                            LoginActivity::class.java
                        )
                    )
                } else {
                    viewPager.setCurrentItem(getItem() + 1, true)
                }
            }



            btnBack.setOnClickListener {
                if (getItem() == 0) {
                    finish()
                } else {
                    viewPager.setCurrentItem(getItem() - 1, true)
                }
            }
        }
    }

    private fun getItem(): Int {
        return binding.viewPager.currentItem
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
}