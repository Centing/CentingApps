package com.c241ps220.centingapps.views.Login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.MainActivity
import com.c241ps220.centingapps.ViewModelFactory.UserViewModelFactory
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.pref.LoginResponse
import com.c241ps220.centingapps.data.pref.UserModel
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.data.retrofit.ApiConfig
import com.c241ps220.centingapps.data.retrofit.ApiService
import com.c241ps220.centingapps.data.retrofit.UserAuth.UserRequest
import com.c241ps220.centingapps.data.retrofit.UserAuth.UserResponse
import com.c241ps220.centingapps.databinding.ActivityLoginBinding
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.Register.RegisterActivity
import com.c241ps220.centingapps.views.SplashScreen.SplashscreenViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = obtainViewModel(this@LoginActivity)

        setupView()
        setupAction()

        loginViewModel.getSession().observe(this) { user ->
            if (user.isLogin == true) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
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

    private fun setupAction() {
        with(binding) {
            signupButton.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            loginButton.setOnClickListener {
                val email = binding.edLoginEmail.text.toString()
                val password = binding.edLoginPassword.text.toString()
                if (!email.isEmpty() || !password.isEmpty()) {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    loginRequest(email, password)
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): LoginViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, UserPreference.getInstance(application))
        return ViewModelProvider(activity, factory).get(LoginViewModel::class.java)
    }

    private fun loginRequest(email: String, password: String) {
        // Buat objek LoginRequest
        val loginRequest = UserRequest(email, password)

        // Panggil fungsi login dari ApiService
        ApiConfig.createApiService().loginUser(loginRequest)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        loginResponse?.let {
                            // Login berhasil, tampilkan data user
                            binding.loadingProgressBar.visibility = View.GONE
                            Log.d("API", "Email: ${it.email}")
                            Log.d("API", "Name: ${it.name}")
                            // Tambahkan logika sesuai kebutuhan setelah login berhasil
//                            JANGAN LUPA YANG STRING HARDCODE DIBAWAH NUNGGU UPDATE DARI CC
                            val user = UserModel(
                                it.name,
                                it.email,
                                "Laki - Laki",
                                "29-03-2003"
                            )
                            loginViewModel.saveSession(user)
                        }
                    } else {
                        // Login gagal
                        binding.loadingProgressBar.visibility = View.GONE
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = errorBody?.let {
                            try {
                                val gson = Gson()
                                val jsonObject = gson.fromJson(it, JsonObject::class.java)
                                jsonObject.get("message").asString
                            } catch (e: Exception) {
                                "Login failed"
                            }
                        } ?: "Login failed"
                        Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    // Koneksi gagal atau error lainnya
                    binding.loadingProgressBar.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

}
