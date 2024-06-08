package com.c241ps220.centingapps.views.Profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityProfileBinding
import com.c241ps220.centingapps.utils.CustomFunction

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        with(binding){
            tvInisial.text = CustomFunction.getInitials(getString(R.string.dummy_name))
        }
    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = "Profile"
        }
    }
}