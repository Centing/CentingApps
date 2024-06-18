package com.c241ps220.centingapps.views.Profile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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
                binding.tvJenisKelamin.text = user.gender
                binding.tvTanggalLahir.text = user.birthDate
            }
        }

        binding.logoutbutton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.profile_title)
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this@ProfileActivity)
        builder.setTitle(R.string.konfirmasi_logout)
        builder.setMessage(R.string.dialog_logout_message)

        builder.setPositiveButton(R.string.ya) { dialog: DialogInterface, which: Int ->
            logout()
        }

        builder.setNegativeButton(R.string.tidak) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
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
