package com.c241ps220.centingapps.views.ResetPassword

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityProfileBinding
import com.c241ps220.centingapps.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        with(binding){
            btCancel.setOnClickListener { finish() }
            btSave.setOnClickListener { isDevelopment(true) }
        }
    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.p_password)
        }
    }

    fun isDevelopment(state: Boolean) {
        with(binding) {
            if (state) {
                divDevelopment.root.visibility = View.VISIBLE
            } else {
                divDevelopment.root.visibility = View.GONE
            }

            divDevelopment.btDismiss.setOnClickListener {
                isDevelopment(false)
            }
        }
    }
}