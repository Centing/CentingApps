package com.c241ps220.centingapps.views.Profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityProfileBinding
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.Login.LoginActivity
import kotlinx.coroutines.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference.getInstance(this)

        setupToolbar()

        CoroutineScope(Dispatchers.Main).launch {
            userPreference.getSession().collect { user ->
                val initials = CustomFunction.getInitials(user.name)
                binding.tvInisial.text = initials
                binding.tvName.text = user.name
                binding.tvEmail.text = user.email
            }
        }

        binding.logoutbutton.setOnClickListener {
            logout()
        }
    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.profile_title)
        }
    }

    private fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            userPreference.logout()
//            finish()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }
}
