package com.c241ps220.centingapps.views.History

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.database.result.DetectionResult
import com.c241ps220.centingapps.databinding.ActivityDetectionUserBinding
import com.c241ps220.centingapps.databinding.ActivityHistoryBinding
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.AnakSection.DetailAnak.DetailAnakActivity
import com.c241ps220.centingapps.views.AnakSection.ListAnak.ListAnakAdapter
import com.c241ps220.centingapps.views.Deteksi.SelectChild.SelectAnakActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    private lateinit var historyActivityViewModel: HistoryActivityViewModel
    private lateinit var adapter: HistoryActivityAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        with(binding){
            val data_child = intent.getParcelableExtra<Child>("DATA_CHILD")

            if (data_child != null) {
                etNameChild.setText(data_child.name)
                etGender.setText(data_child.gender)
                etBirthChild.setText(data_child.birthDate)
                var birtDate = data_child.birthDate
                var age = birtDate.let { CustomFunction.calculateAgeInMonths(it) }
                etAge.setText(age.toString())
                etBirthHeight.setText(data_child.heightBirth.toString())
                adapter = HistoryActivityAdapter()
                rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
                rvHistory.setHasFixedSize(true)
                rvHistory.adapter = adapter

                historyActivityViewModel = obtainViewModel(this@HistoryActivity)

                historyActivityViewModel.getHistoryByChild(data_child.id).observe(this@HistoryActivity) { resultList ->
                    if (resultList.size > 0) {
                        adapter.setListDetectionResult(resultList)
                        rvHistory.visibility = View.VISIBLE
                        tvEmpty.visibility = View.GONE
                    }else {
                        rvHistory.visibility = View.GONE
                        tvEmpty.visibility = View.VISIBLE
                    }
                }

                adapter.setOnItemClickCallback(object : HistoryActivityAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: DetectionResult) {

                    }
                })
            }else{
                finish()
            }

        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): HistoryActivityViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(HistoryActivityViewModel::class.java)
    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(com.c241ps220.centingapps.R.string.descHistory)
        }
    }
}