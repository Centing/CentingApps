package com.c241ps220.centingapps.views.Register

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.MainActivity
import com.c241ps220.centingapps.ViewModelFactory.UserViewModelFactory
import com.c241ps220.centingapps.databinding.ActivityRegisterBinding
import com.c241ps220.centingapps.views.Login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        // playAnimation()
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

    private fun setupViewModel() {
        val factory = UserViewModelFactory.getInstance(this)
        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        registerViewModel.registerResult.observe(this, Observer { response ->
            if (!response.error!!) {
                binding.loadingProgressBar.visibility = View.GONE
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.loadingProgressBar.visibility = View.GONE
                Toast.makeText(this, "Registration failed: ${response.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupAction() {
        with(binding) {
            loginButton.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }

            registerButton.setOnClickListener {
                val name = edRegisterUser.text.toString()
                val email = edRegisterEmail.text.toString()
                val password = edRegisterPassword.text.toString()
                binding.loadingProgressBar.visibility = View.VISIBLE
                registerViewModel.register(name, email, password)
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}
