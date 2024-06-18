package com.c241ps220.centingapps.views.AnakSection.DetailAnak

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.database.AppDatabase
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.database.child.ChildDao
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.databinding.ActivityDetailAnakBinding
import kotlinx.coroutines.launch
import java.lang.String
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.Int
import kotlin.with


class DetailAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAnakBinding

    private var isSelectedGender = 0 // 0 for Laki-Laki, 1 for Perempuan
    private var isSelectedHeightBirth = 0f

    private lateinit var detailAnakViewModel: DetailAnakViewModel
    var id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        detailAnakViewModel = obtainViewModel(this@DetailAnakActivity)


        with(binding) {
            val data_child = intent.getParcelableExtra<Child>("DATA_CHILD")

            if (data_child != null) {

                id = data_child.id

                etNameChild.setText(data_child.name)
                etBirthChild.setText(data_child.birthDate)
                tvValueHeightBirth.text = data_child.heightBirth.toString()
                sbHeightBirth.progress = data_child.heightBirth.toInt()
                isSelectedHeightBirth = data_child.heightBirth
                if (data_child.gender.equals("Laki-Laki")){
                    genderMale()
                }else{
                    genderFemale()
                }

                setupGender()
                setupSeekBar()
            }else{
                finish()
            }

            etBirthChild.setOnClickListener { showDatePickerDialog() }

            btSave.setOnClickListener {
                if (etNameChild.text.toString().isEmpty() || etBirthChild.text.toString().isEmpty()) {
                    Toast.makeText(this@DetailAnakActivity, "Opps Data Belum Lengkap,\nHarap lengkapi inputan yang tersedia!", Toast.LENGTH_SHORT).show()
                }else{
                    val builder = AlertDialog.Builder(this@DetailAnakActivity)
                    builder.setTitle(R.string.konfirmasi)
                    builder.setMessage(R.string.dialog_update_child_message)

                    builder.setPositiveButton(R.string.ya) { dialog: DialogInterface, which: Int ->
                        val dataChild = Child(
                            id = id,
                            name = etNameChild.text.toString(),
                            birthDate = etBirthChild.text.toString(),
                            gender = if (isSelectedGender == 0) "Laki-Laki" else "Perempuan",
                            heightBirth = isSelectedHeightBirth
                        )
                        detailAnakViewModel.update(dataChild)
                        finish()
                    }

                    builder.setNegativeButton(R.string.tidak) { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }

                    val dialog = builder.create()
                    dialog.show()
                }
            }

            btHapus.setOnClickListener {
                val builder = AlertDialog.Builder(this@DetailAnakActivity)
                builder.setTitle(R.string.konfirmasi)
                builder.setMessage(R.string.dialog_delete_child_message)

                builder.setPositiveButton(R.string.ya) { dialog: DialogInterface, which: Int ->
                    val dataChild = Child(
                        id = id,
                        name = etNameChild.text.toString(),
                        birthDate = etBirthChild.text.toString(),
                        gender = if (isSelectedGender == 0) "Laki-Laki" else "Perempuan",
                        heightBirth = isSelectedHeightBirth
                    )
                    detailAnakViewModel.delete(dataChild)
                    finish()
                }

                builder.setNegativeButton(R.string.tidak) { dialog: DialogInterface, which: Int ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailAnakViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, UserPreference.getInstance(application))
        return ViewModelProvider(activity, factory).get(DetailAnakViewModel::class.java)
    }

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(com.c241ps220.centingapps.R.string.tb_child_detail)
        }
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

    private fun genderMale() {
        with(binding){
            isSelectedGender = 0 // "Laki-Laki"
            divGenderLaki.setBackgroundResource(R.drawable.rectangle_stroke2)
            divGenderPerempuan.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
        }
    }

    private fun genderFemale() {
        with(binding) {
            isSelectedGender = 1 // "Perempuan"
            divGenderLaki.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
            divGenderPerempuan.setBackgroundResource(R.drawable.rectangle_stroke2)
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
