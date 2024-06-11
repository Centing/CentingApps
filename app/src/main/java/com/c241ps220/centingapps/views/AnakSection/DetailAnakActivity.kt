package com.c241ps220.centingapps.views.AnakSection

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.data.database.AppDatabase
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.database.child.ChildDao
import com.c241ps220.centingapps.databinding.ActivityDetailAnakBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DetailAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAnakBinding
    private lateinit var database: AppDatabase
    private lateinit var childDao: ChildDao
    private var childId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        childId = intent.getIntExtra("child_id", 0)
        database = AppDatabase.getInstance(this)
        childDao = database.childDao()

        setupToolbar()
        loadChildDetails()

        with(binding) {
            etBirthChild.setOnClickListener { showDatePickerDialog() }

            btSave.setOnClickListener {
                // Save logic here
                finish()
            }

            btHapus.setOnClickListener {
                deleteChild()
            }
        }
    }

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.tb_child_detail)
        }
    }

    private fun loadChildDetails() {
        lifecycleScope.launch {
            val child = childDao.getChildById(childId)
            if (child != null) {
                binding.etNameChild.setText(child.name)
                binding.etBirthChild.setText(child.birthDate)
                // Set gender and height birth accordingly
            }
        }
    }

    private fun deleteChild() {
        lifecycleScope.launch {
            val child = childDao.getChildById(childId)
            if (child != null) {
                childDao.deleteChild(child)
                finish()
            }
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
