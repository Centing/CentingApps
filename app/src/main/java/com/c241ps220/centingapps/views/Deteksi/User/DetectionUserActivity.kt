package com.c241ps220.centingapps.views.Deteksi.User

import TFLiteModel
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.databinding.ActivityAddAnakBinding
import com.c241ps220.centingapps.databinding.ActivityDetectionUserBinding
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.Deteksi.Result.ResultDetectByGuestActivity
import com.c241ps220.centingapps.views.Deteksi.Result.ResultDetectByUserActivity
import com.c241ps220.centingapps.views.Deteksi.SelectChild.SelectAnakActivity

class DetectionUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectionUserBinding

    private var isSelectedHeightLatest = 60f

    private lateinit var tfliteModel: TFLiteModel
    private var isSelectedAge = 0
    private var isSelectedGender = 0 // 0 Laki, 1 Perempuan


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetectionUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tfliteModel = TFLiteModel(this)

        setupToolbar()
        setupSeekBar()

        with(binding){

            btSelectChild.setOnClickListener {
                val intent = Intent(this@DetectionUserActivity, SelectAnakActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_ANAK)
            }

            detectionHere()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ANAK && resultCode == RESULT_OK) {
//            val selectedAnak = data?.getStringExtra("SELECTED_ANAK")
            val selectedAnak = data?.getParcelableExtra("SELECTED_ANAK") as? Child

            with(binding){
                etNameChild.setText(selectedAnak?.name)
                etGender.setText(selectedAnak?.gender)
                etBirthChild.setText(selectedAnak?.birthDate)
                var birtDate = selectedAnak?.birthDate
                var age = birtDate?.let { CustomFunction.calculateAgeInMonths(it) }
                etAge.setText(age.toString())
                etBirthHeight.setText(selectedAnak?.heightBirth.toString())

                tvValueHeightLatest.setText(selectedAnak?.heightBirth.toString())
                sbHeightLatest.progress = selectedAnak?.heightBirth!!.toInt()
                isSelectedHeightLatest = selectedAnak.heightBirth
            }
        }
    }

    private fun detectionHere(){
        with(binding){
            btDetectionNow.setOnClickListener {
                var sNameChild = etNameChild.text.toString()
                if (sNameChild.equals(getString(R.string.please_select_child))){
                    Toast.makeText(this@DetectionUserActivity, R.string.please_select_child, Toast.LENGTH_SHORT).show()
                }else{

                    val builder = AlertDialog.Builder(this@DetectionUserActivity)
                    builder.setTitle(R.string.konfirmasi)
                    builder.setMessage(R.string.dialog_detection_now_message)

                    builder.setPositiveButton(R.string.ya) { dialog: DialogInterface, which: Int ->
                        val dialog = builder.create()
                        isSelectedAge = etAge.text.toString().toInt()
                        if (etGender.text.toString().equals("Perempuan")){
                            isSelectedGender = 1
                        } else{
                            isSelectedGender = 0
                        }

                        val result = tfliteModel.runInference(isSelectedAge, isSelectedGender, isSelectedHeightLatest)

                        Log.d("RBA", "detectionHere: " +
                                "\n Age: $isSelectedAge" +
                                "\n Gender: $isSelectedGender" +
                                "\n Latest Height: $isSelectedHeightLatest" +
                                "\n Name: ${etNameChild.text}" +
                                "\n Birth: ${etBirthChild.text}" +
                                "\n Height Birth: ${etBirthHeight.text}" +
                                "\n Result : $result")

                        val intent = Intent(this@DetectionUserActivity, ResultDetectByUserActivity::class.java)
                        intent.putExtra("NAME", etNameChild.text.toString())
                        intent.putExtra("GENDER", isSelectedGender)
                        intent.putExtra("BIRTH_DATE", etBirthChild.text.toString())
                        intent.putExtra("AGE", isSelectedAge)
                        intent.putExtra("HEIGHT_BIRTH", etBirthHeight.text.toString())
                        intent.putExtra("HEIGHT_LATEST", isSelectedHeightLatest)
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

                    builder.setNegativeButton(R.string.tidak) { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }

                    val dialog = builder.create()
                    dialog.show()



                }

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