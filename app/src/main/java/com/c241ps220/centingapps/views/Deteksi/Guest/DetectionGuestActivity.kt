package com.c241ps220.centingapps.views.Deteksi.Guest

import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.data.database.AppDatabase
import com.c241ps220.centingapps.data.database.result.DetectionResult
import com.c241ps220.centingapps.data.database.result.DetectionResultDao
import com.c241ps220.centingapps.databinding.ActivityDetectionGuestBinding
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.nio.ByteOrder
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class DetectionGuestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionGuestBinding
    private lateinit var interpreter: Interpreter
    private val stepSize = 0.1f
    private var isSelectedGender = 0f // 0 Laki, 1 Perempuan
    private var isSelectedSusu = "ASI"
    private var isSelectedHeightBirth = 40f
    private var isSelectedHeightLatest = 60f
    private var isSelectedWeightBirth = 2.4f
    private var isSelectedWeightLatest = 4f
    private var isSelectedAge = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        interpreter = Interpreter(loadModelFile("testing2.tflite"))
        setupToolbar()
        setupGender()
        setupSusu()
        setupSeekBar()
        setupAddRemove()
        detectionHere()
    }

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.title_detection)
        }
    }

    private fun setupGender() {
        with(binding) {
            divGenderLaki.setOnClickListener {
                isSelectedGender = 0f // "Laki-Laki"
                divGenderLaki.setBackgroundResource(R.drawable.rectangle_stroke2)
                divGenderPerempuan.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
            }
            divGenderPerempuan.setOnClickListener {
                isSelectedGender = 1f // "Perempuan"
                divGenderLaki.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
                divGenderPerempuan.setBackgroundResource(R.drawable.rectangle_stroke2)
            }
        }
    }

    private fun setupSusu() {
        with(binding) {
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

    private fun setupSeekBar() {
        with(binding) {
            sbHeightBirth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    tvValueHeightBirth.text = "$progress Cm"
                    isSelectedHeightBirth = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

            sbHeightLatest.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    tvValueHeightLatest.text = "$progress Cm"
                    isSelectedHeightLatest = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

    private fun setupAddRemove() {
        with(binding) {
            ivRemoveWeightBirth.setOnClickListener {
                isSelectedWeightBirth -= stepSize
                isSelectedWeightBirth = isSelectedWeightBirth.coerceAtLeast(0f)
                tvValueWeightBirth.text = String.format("%.1f Cm", isSelectedWeightBirth)
            }
            ivAddWeightBirth.setOnClickListener {
                isSelectedWeightBirth += stepSize
                isSelectedWeightBirth = isSelectedWeightBirth.coerceAtLeast(0f)
                tvValueWeightBirth.text = String.format("%.1f Cm", isSelectedWeightBirth)
            }

            ivRemoveWeightLatest.setOnClickListener {
                isSelectedWeightLatest -= stepSize
                isSelectedWeightLatest = isSelectedWeightLatest.coerceAtLeast(0f)
                tvValueWeightLatest.text = String.format("%.1f Kg", isSelectedWeightLatest)
            }
            ivAddWeightLatest.setOnClickListener {
                isSelectedWeightLatest += stepSize
                isSelectedWeightLatest = isSelectedWeightLatest.coerceAtLeast(0f)
                tvValueWeightLatest.text = String.format("%.1f Kg", isSelectedWeightLatest)
            }

            var textUmur = getString(R.string.age)
            ivRemoveAge.setOnClickListener {
                isSelectedAge -= 1
                isSelectedAge = isSelectedAge.coerceAtLeast(0f)
                tvValueAge.text = "$isSelectedAge $textUmur"
            }
            ivAddAge.setOnClickListener {
                isSelectedAge += 1
                isSelectedAge = isSelectedAge.coerceAtLeast(0f)
                tvValueAge.text = "$isSelectedAge $textUmur"
            }
        }
    }

    private fun detectionHere() {
        val database = AppDatabase.getInstance(this)
        val detectionResultDao = database.detectionResultDao()

        with(binding) {
            btDetectionNow.setOnClickListener {
                if (isSelectedAge <= 0) {
                    Toast.makeText(
                        this@DetectionGuestActivity,
                        "Opps Data Belum Lengkap,\nHarap lengkapi inputan yang tersedia!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val input = floatArrayOf(
                        isSelectedGender,
                        isSelectedAge,
                        isSelectedWeightBirth,
                        isSelectedHeightBirth,
                        isSelectedWeightLatest,
                        isSelectedHeightLatest
                    )
                    val result = doInference(input)
                    val statusStunting = if (result >= 0.5f) "Stunting" else "Tidak Stunting"
                    Toast.makeText(
                        this@DetectionGuestActivity,
                        "Status Stunting: $statusStunting",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Save the result to the database
                    val detectionResult = DetectionResult(
                        childId = 0,  // For guest mode, we can set a default value or handle it accordingly
                        age = isSelectedAge,
                        height = isSelectedHeightLatest,
                        weight = isSelectedWeightLatest,
                        result = statusStunting
                    )
                    lifecycleScope.launch {
                        detectionResultDao.insertDetectionResult(detectionResult)
                    }
                }
            }
        }
    }


    private fun loadModelFile(filename: String): ByteBuffer {
        val fileDescriptor = assets.openFd(filename)
        val inputStream = fileDescriptor.createInputStream()
        val byteArray = ByteArray(fileDescriptor.length.toInt())
        inputStream.read(byteArray)
        inputStream.close()
        val byteBuffer = ByteBuffer.allocateDirect(byteArray.size)
        byteBuffer.order(ByteOrder.nativeOrder())
        byteBuffer.put(byteArray)
        return byteBuffer
    }

    private fun doInference(input: FloatArray): Float {
        val byteBuffer = ByteBuffer.allocateDirect(6 * 4).apply {
            order(ByteOrder.nativeOrder())
            for (value in input) {
                putFloat(value)
            }
        }

        val output = TensorBuffer.createFixedSize(intArrayOf(1, 1), DataType.FLOAT32)
        interpreter.run(byteBuffer, output.buffer.rewind())
        return output.floatArray[0]
    }

    override fun onDestroy() {
        interpreter.close()
        super.onDestroy()
    }
}
