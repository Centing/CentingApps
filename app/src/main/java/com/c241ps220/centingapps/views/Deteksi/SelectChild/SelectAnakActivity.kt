package com.c241ps220.centingapps.views.Deteksi.SelectChild

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityListAnakBinding
import com.c241ps220.centingapps.databinding.ActivitySelectAnakBinding
import com.c241ps220.centingapps.views.AnakSection.AddAnakActivity
import com.c241ps220.centingapps.views.AnakSection.DetailAnakActivity

class SelectAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectAnakBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        with(binding) {

//            Dummy, kalau sudah tidak kepake hapus aja
            divDummyList.setOnClickListener {
                finish()
            }
        }
    }
    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(R.string.select_child)
        }
    }

}