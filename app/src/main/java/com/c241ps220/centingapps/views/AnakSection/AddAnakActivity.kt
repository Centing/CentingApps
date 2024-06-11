package com.c241ps220.centingapps.views.AnakSection

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.data.database.AppDatabase
import com.c241ps220.centingapps.databinding.ActivityAddAnakBinding
import java.text.SimpleDateFormat
import java.util.*

class AddAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAnakBinding
    private var isSelectedGender = 0f // 0 for Laki-Laki, 1 for Perempuan
    private var isSelectedHeightBirth = 0f
    private lateinit var viewModel: AddChildViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val childDao = AppDatabase.getInstance(application).childDao()
        val factory = AddChildViewModelFactory(childDao)
        viewModel = ViewModelProvider(this, factory).get(AddChildViewModel::class.java)

        setupToolbar()
        setupGender()
        setupSeekBar()

        with(binding) {
            etBirthChild.setOnClickListener { showDatePickerDialog() }

            btSave.setOnClickListener {
                val name = etNameChild.text.toString()
                val birthDate = etBirthChild.text.toString()
                val gender = if (isSelectedGender == 0f) "Laki-Laki" else "Perempuan"
                val heightBirth = isSelectedHeightBirth

                viewModel.addChild(name, birthDate, gender, heightBirth)
                finish()
            }
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

    private fun setupSeekBar() {
        with(binding) {
            sbHeightBirth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    tvValueHeightBirth.text = "$progress Cm"
                    isSelectedHeightBirth = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    tvValueHeightBirth.text = "${seekBar.progress} Cm"
                    isSelectedHeightBirth = seekBar.progress.toFloat()
                }
            })
        }
    }

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = resources.getString(R.string.tb_add_child)
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DatePickerDialogStyle,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
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