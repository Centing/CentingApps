package com.c241ps220.centingapps.views.Deteksi.Result

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityHistoryBinding
import com.c241ps220.centingapps.databinding.ActivityResultDetectByUserBinding

class ResultDetectByUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultDetectByUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultDetectByUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        with(binding){

        }
    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(com.c241ps220.centingapps.R.string.result_detection_title)
        }
    }
}