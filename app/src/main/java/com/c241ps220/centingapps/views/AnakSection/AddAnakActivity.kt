package com.c241ps220.centingapps.views.AnakSection

import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityAddAnakBinding
import com.c241ps220.centingapps.databinding.ActivityListAnakBinding
import com.c241ps220.centingapps.utils.CustomFunction
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAnakBinding
    private var isSelectedGender = 0f // 0 Laki, 1 Perempuan
    private var isSelectedHeightBirth = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        setupGender()
        setupSeekBar()

        with(binding){
            etBirthChild.setOnClickListener { showDatePickerDialog() }

            btSave.setOnClickListener {
                finish()
            }

        }

    }

    private fun setupGender(){
        with(binding){
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
        }
    }


    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(R.string.tb_add_child)
        }
    }

    private fun showDatePickerDialog() {
        // Get current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Show DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DatePickerDialogStyle,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // Format the selected date and set it to EditText
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                binding.etBirthChild.setText(dateFormat.format(selectedDate.time))
            },
            year, month, day
        )

        datePickerDialog.show()
    }
}