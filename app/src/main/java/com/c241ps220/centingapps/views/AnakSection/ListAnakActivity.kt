package com.c241ps220.centingapps.views.AnakSection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityListAnakBinding

class ListAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        with(binding){
            fabChild.setOnClickListener {
                startActivity(Intent(this@ListAnakActivity, AddAnakActivity::class.java))
            }
        }

    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(R.string.tb_child)
        }
    }
}