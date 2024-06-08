package com.c241ps220.centingapps.views.Deteksi.User

import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityAddAnakBinding
import com.c241ps220.centingapps.databinding.ActivityDetectionUserBinding

class DetectionUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectionUserBinding

    private var isSelectedHeightLatest = 60f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetectionUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupSeekBar()

        with(binding){
        }

    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(com.c241ps220.centingapps.R.string.title_detection)
        }
    }

    private fun setupSeekBar(){
        with(binding){


            sbHeightLatest.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    tvValueHeightLatest.setText("$progress Cm")
                    isSelectedHeightLatest = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    var progress = seekBar.progress
                    tvValueHeightLatest.setText("$progress Cm")
                    isSelectedHeightLatest = progress.toFloat()
                }
            })
        }
    }
}