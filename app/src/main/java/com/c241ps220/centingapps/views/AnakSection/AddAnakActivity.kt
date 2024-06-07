package com.c241ps220.centingapps.views.AnakSection

import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        with(binding){
            etBirthChild.setOnClickListener { showDatePickerDialog() }

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