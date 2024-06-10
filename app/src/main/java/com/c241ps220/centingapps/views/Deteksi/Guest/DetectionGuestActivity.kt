package com.c241ps220.centingapps.views.Deteksi.Guest

import TFLiteModel
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityDetectionGuestBinding
import com.c241ps220.centingapps.databinding.ActivityZoomImageBinding
import java.nio.ByteBuffer
import java.nio.ByteOrder
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import android.util.Log
import com.c241ps220.centingapps.views.Deteksi.Result.ResultDetectByGuestActivity

class DetectionGuestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionGuestBinding

//    private lateinit var interpreter: Interpreter

    private val stepSize = 0.1f
//    private var currentValue = 0f
//     Value Selectected
    private var isSelectedGender = 0 // 0 Laki, 1 Perempuan
    private var isSelectedSusu = "ASI"
    private var isSelectedHeightBirth = 40f
    private var isSelectedHeightLatest = 60f
    private var isSelectedWeightBirth = 2.4f
    private var isSelectedWeightLatest = 4f
    private var isSelectedAge = 0

    private lateinit var tfliteModel: TFLiteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetectionGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi model
//        interpreter = Interpreter(loadModelFile("pradika.tflite"))
        tfliteModel = TFLiteModel(this)

        setupToolbar()
        setupGender()
        setupSusu()
        setupSeekBar()
        setupAddRemove()
        detectionHere()
    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(com.c241ps220.centingapps.R.string.title_detection)
        }
    }

    private fun setupGender(){
        with(binding){
            divGenderLaki.setOnClickListener {
                isSelectedGender = 0 // "Laki-Laki"
                divGenderLaki.setBackgroundResource(R.drawable.rectangle_stroke2)
                divGenderPerempuan.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
            }
            divGenderPerempuan.setOnClickListener {
                isSelectedGender = 1 // "Perempuan"
                divGenderLaki.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
                divGenderPerempuan.setBackgroundResource(R.drawable.rectangle_stroke2)
            }
        }
    }

    private fun setupSusu(){
        with(binding){
            divASI.setOnClickListener {
                isSelectedSusu = "ASI"
                divASI.setBackgroundResource(R.drawable.rectangle_stroke2)
                divSusuFormuka.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
            }
            divSusuFormuka.setOnClickListener {
                isSelectedSusu = "Formula"
                divASI.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
                divSusuFormuka.setBackgroundResource(R.drawable.rectangle_stroke2)
            }
        }
    }

    private fun setupSeekBar(){
        with(binding){


            sbHeightBirth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    tvValueHeightBirth.setText("$progress Cm")
                    isSelectedHeightBirth = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    var progress = seekBar.progress
                    tvValueHeightBirth.setText("$progress Cm")
                    isSelectedHeightBirth = progress.toFloat()
                }
            })

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

    private fun setupAddRemove(){
        with(binding){
//            Berat Lahir
            ivRemoveWeightBirth.setOnClickListener {
                isSelectedWeightBirth -= stepSize
                isSelectedWeightBirth = Math.max(isSelectedWeightBirth, 0f)
                tvValueWeightBirth.text = String.format("%.1f Cm", isSelectedWeightBirth)
            }
            ivAddWeightBirth.setOnClickListener {
                isSelectedWeightBirth += stepSize
                isSelectedWeightBirth = Math.max(isSelectedWeightBirth, 0f)
                tvValueWeightBirth.text = String.format("%.1f Cm", isSelectedWeightBirth)
            }

//            Berat Sekarang
            ivRemoveWeightLatest.setOnClickListener {
                isSelectedWeightLatest -= stepSize
                isSelectedWeightLatest = Math.max(isSelectedWeightLatest, 0f)
                tvValueWeightLatest.text = String.format("%.1f Kg", isSelectedWeightLatest)
            }
            ivAddWeightLatest.setOnClickListener {
                isSelectedWeightLatest += stepSize
                isSelectedWeightLatest = Math.max(isSelectedWeightLatest, 0f)
                tvValueWeightLatest.text = String.format("%.1f Kg", isSelectedWeightLatest)
            }

//            Umur
            var textUmur = getString(R.string.age)
            ivRemoveAge.setOnClickListener {
                isSelectedAge -= 1
                isSelectedAge = Math.max(isSelectedAge, 0)
                tvValueAge.text = "$isSelectedAge $textUmur"
            }
            ivAddAge.setOnClickListener {
                isSelectedAge += 1
                isSelectedAge = Math.max(isSelectedAge, 0)
                tvValueAge.text = "$isSelectedAge $textUmur"
            }
        }
    }

    private fun detectionHere(){
        with(binding){
            btDetectionNow.setOnClickListener {
//                val input = floatArrayOf(gender, age, birthWeight, birthHeight, weight, height)
                if (isSelectedAge <= 0){
                    Toast.makeText(this@DetectionGuestActivity, "Opps Data Belum Lengkap,\nHarap lengkapi inputan yang tersedia!", Toast.LENGTH_SHORT).show()
                }else{
                    val result = tfliteModel.runInference(isSelectedAge, isSelectedGender, isSelectedHeightLatest)

                    val intent = Intent(this@DetectionGuestActivity, ResultDetectByGuestActivity::class.java)
                    intent.putExtra("AGE", isSelectedAge)
                    intent.putExtra("HEIGHT_BIRTH", isSelectedHeightBirth)
                    intent.putExtra("HEIGHT_LATEST", isSelectedHeightLatest)
                    intent.putExtra("GENDER", isSelectedGender)
                    intent.putExtra("STATUS", result)
                    startActivity(intent)

                    // Interpretasi hasil
                    when (result) {
                        0 -> Log.d("TFLiteResult", "Result: Normal")
                        1 -> Log.d("TFLiteResult", "Result: Severely stunting")
                        2 -> Log.d("TFLiteResult", "Result: Stunting")
                        3 -> Log.d("TFLiteResult", "Result: Tinggi")
                        else -> Log.e("TFLiteResult", "Error in finding the result.")
                    }



                }




            }
        }

    }

    override fun onDestroy() {
        tfliteModel.close()
        super.onDestroy()
    }
}