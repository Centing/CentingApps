package com.c241ps220.centingapps.views.Deteksi.Result

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityHistoryBinding
import com.c241ps220.centingapps.databinding.ActivityResultDetectByUserBinding

class ResultDetectByUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultDetectByUserBinding

    private var isSelectedName = ""
    private var isSelectedBirthDate = ""
    private var isSelectedAge = 0
    private var isSelectedHeightBirth = 0f
    private var isSelectedHeightLatest = 0f
    private var isSelectedGender = 0
    private var isSelectedStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultDetectByUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()


        with(binding){
            val intent = intent

            // Mengecek apakah intent memiliki data dengan key "message"
            if (!intent.hasExtra("AGE")) {
                finish() // Mengakhiri aktivitas jika tidak ada data
                return
            }else{
                isSelectedName = intent.getStringExtra("NAME").toString()
                isSelectedGender = intent.getIntExtra("GENDER", 0)
                isSelectedBirthDate = intent.getStringExtra("BIRTH_DATE").toString()
                isSelectedAge = intent.getIntExtra("AGE", 0)
                isSelectedHeightLatest = intent.getFloatExtra("HEIGHT_LATEST", 0f)
                isSelectedStatus = intent.getIntExtra("STATUS", 0)

                if (isSelectedGender == 0){
                    etGender.setText(getString(R.string.laki_laki))
                }

                if (isSelectedGender == 1){
                    etGender.setText(getString(R.string.perempuan))
                }

                etNameChild.setText(intent.getStringExtra("NAME"))
                etBirthChild.setText(intent.getStringExtra("BIRTH_DATE"))
                etAge.setText(isSelectedAge.toString())
                etBirthHeight.setText(intent.getStringExtra("HEIGHT_BIRTH"))
                etLatestHeight.setText(isSelectedHeightLatest.toString())

                when (isSelectedStatus) {
                    0 -> {
                        tvStatusDetection.text = getResources().getString(R.string.status_0)
                        tvDetectionValue.text= getResources().getString(R.string.result_detection_value_plus)
                        divStatusPlus.root.visibility = View.VISIBLE
                        divStatusMinus.root.visibility = View.GONE
                        animationSad.setAnimation(R.raw.no_stunting) // Mengatur animasi dari raw resource
                        animationSad.playAnimation()
                    }
                    1 -> {
                        tvStatusDetection.text = getResources().getString(R.string.status_1)
                        tvDetectionValue.text= getResources().getString(R.string.result_detection_value_minus)
                        divStatusPlus.root.visibility = View.GONE
                        divStatusMinus.root.visibility = View.VISIBLE
                        animationSad.setAnimation(R.raw.stunting) // Mengatur animasi dari raw resource
                        animationSad.playAnimation()
                    }
                    2 -> {
                        tvStatusDetection.text = getResources().getString(R.string.status_2)
                        tvDetectionValue.text= getResources().getString(R.string.result_detection_value_minus)
                        divStatusPlus.root.visibility = View.GONE
                        divStatusMinus.root.visibility = View.VISIBLE
                        animationSad.setAnimation(R.raw.stunting) // Mengatur animasi dari raw resource
                        animationSad.playAnimation()

                    }
                    3 -> {
                        tvStatusDetection.text = getResources().getString(R.string.status_3)
                        tvDetectionValue.text= getResources().getString(R.string.result_detection_value_plus)
                        divStatusPlus.root.visibility = View.VISIBLE
                        divStatusMinus.root.visibility = View.GONE
                        animationSad.setAnimation(R.raw.no_stunting) // Mengatur animasi dari raw resource
                        animationSad.playAnimation()
                    }
                    else -> {
                        tvStatusDetection.text = "Error in finding the result."
                        tvDetectionValue.text = "Error in finding the result."
                        divStatusPlus.root.visibility = View.GONE
                        divStatusMinus.root.visibility = View.GONE
                        animationSad.setAnimation(R.raw.on_development) // Mengatur animasi dari raw resource
                        animationSad.playAnimation()
                    }
                }
            }
        }

    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(com.c241ps220.centingapps.R.string.result_detection_title)
        }
    }
}