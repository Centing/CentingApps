package com.c241ps220.centingapps.views.AnakSection.AddAnak

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.databinding.ActivityAddAnakBinding
import java.text.SimpleDateFormat
import java.util.*

class AddAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAnakBinding
    private var isSelectedGender = 0 // 0 for Laki-Laki, 1 for Perempuan
    private var isSelectedHeightBirth = 40f

    private lateinit var addAnakViewModel: AddAnakViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addAnakViewModel = obtainViewModel(this@AddAnakActivity)

        setupToolbar()
        setupGender()
        setupSeekBar()

        with(binding) {
            etBirthChild.setOnClickListener { showDatePickerDialog() }

            btSave.setOnClickListener {
                if (etNameChild.text.toString().isEmpty() || etBirthChild.text.toString().isEmpty()) {
                    Toast.makeText(this@AddAnakActivity, "Opps Data Belum Lengkap,\nHarap lengkapi inputan yang tersedia!", Toast.LENGTH_SHORT).show()
                }else{
                    val dataChild = Child(
                        name = etNameChild.text.toString(),
                        birthDate = etBirthChild.text.toString(),
                        gender = if (isSelectedGender == 0) "Laki-Laki" else "Perempuan",
                        heightBirth = isSelectedHeightBirth
                    )
                    addAnakViewModel.insert(dataChild)
                    finish()
                }

            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): AddAnakViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddAnakViewModel::class.java)
    }


    private fun setupGender() {
        with(binding) {
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