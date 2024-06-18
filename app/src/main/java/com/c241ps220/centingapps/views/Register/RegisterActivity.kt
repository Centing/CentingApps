package com.c241ps220.centingapps.views.Register

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.MainActivity
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.UserViewModelFactory
import com.c241ps220.centingapps.data.pref.UserModel
import com.c241ps220.centingapps.data.retrofit.ApiConfig
import com.c241ps220.centingapps.data.retrofit.UserLoginAuth.UserLoginRequest
import com.c241ps220.centingapps.data.retrofit.UserLoginAuth.UserLoginResponse
import com.c241ps220.centingapps.data.retrofit.UserRegisterAuth.UserRegisterRequest
import com.c241ps220.centingapps.data.retrofit.UserRegisterAuth.UserRegisterResponse
import com.c241ps220.centingapps.databinding.ActivityRegisterBinding
import com.c241ps220.centingapps.views.Login.LoginActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
//    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
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

    private fun setupAction() {
        with(binding) {
            loginButton.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }

            registerButton.setOnClickListener {
                val name = edRegisterUser.text.toString()
                val email = edRegisterEmail.text.toString()
                val password = edRegisterPassword.text.toString()
                if (!email.isEmpty() || !password.isEmpty() || !name.isEmpty()) {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    registerRequest(email, password, name)
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        getString(R.string.complete_field),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun registerRequest(
        email: String,
        password: String,
        name: String,
        role: String = "CT_USER"
    ) {
        val registerRequest = UserRegisterRequest(email, password, name, role)

        // Panggil fungsi login dari ApiService
        ApiConfig.createApiService().registerUser(registerRequest)
            .enqueue(object : Callback<UserRegisterResponse> {
                override fun onResponse(
                    call: Call<UserRegisterResponse>,
                    response: Response<UserRegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val registerResponse = response.body()
                        registerResponse?.let {
                            // Login berhasil, tampilkan data user
                            binding.loadingProgressBar.visibility = View.GONE
                            Log.d("API", "Email: ${it.message}")
                            Log.d("API", "Name: ${it.userId}")
                            Toast.makeText(this@RegisterActivity, "${it.message}", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        }
                    } else {
                        // Login gagal
                        binding.loadingProgressBar.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, "Registration failed", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<UserRegisterResponse>, t: Throwable) {
                    // Koneksi gagal atau error lainnya
                    binding.loadingProgressBar.visibility = View.GONE
                    Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

}
