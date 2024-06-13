package com.c241ps220.centingapps.views.Deteksi.User

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.databinding.ActivityAddAnakBinding
import com.c241ps220.centingapps.databinding.ActivityDetectionUserBinding
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.Deteksi.SelectChild.SelectAnakActivity

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

            btSelectChild.setOnClickListener {
                val intent = Intent(this@DetectionUserActivity, SelectAnakActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_ANAK)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ANAK && resultCode == RESULT_OK) {
//            val selectedAnak = data?.getStringExtra("SELECTED_ANAK")
            val selectedAnak = data?.getParcelableExtra("SELECTED_ANAK") as? Child

            with(binding){
                etNameChild.setText(selectedAnak?.name)
                etBirthChild.setText(selectedAnak?.birthDate)
                var birtDate = selectedAnak?.birthDate
                var age = birtDate?.let { CustomFunction.calculateAgeInMonths(it) }
                etAge.setText(age.toString())
                etBirthHeight.setText(selectedAnak?.heightBirth.toString())
            }
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

    companion object {
        const val REQUEST_CODE_ANAK = 1
    }
}