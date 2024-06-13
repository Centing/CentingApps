package com.c241ps220.centingapps.views.History

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.databinding.ActivityDetectionUserBinding
import com.c241ps220.centingapps.databinding.ActivityHistoryBinding
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.Deteksi.SelectChild.SelectAnakActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    var id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        with(binding){
            val data_child = intent.getParcelableExtra<Child>("DATA_CHILD")

            if (data_child != null) {
                etNameChild.setText(data_child.name)
                etBirthChild.setText(data_child.birthDate)
                var birtDate = data_child.birthDate
                var age = birtDate.let { CustomFunction.calculateAgeInMonths(it) }
                etAge.setText(age.toString())
                etBirthHeight.setText(data_child.heightBirth.toString())
            }

        }

    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(com.c241ps220.centingapps.R.string.descHistory)
        }
    }
}