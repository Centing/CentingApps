package com.c241ps220.centingapps.views.AnakSection.ListAnak

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.database.AppDatabase
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.database.child.ChildDao
import com.c241ps220.centingapps.databinding.ActivityListAnakBinding
import com.c241ps220.centingapps.views.AnakSection.AddAnak.AddAnakActivity
import com.c241ps220.centingapps.views.AnakSection.DetailAnakActivity
import com.c241ps220.centingapps.views.Deteksi.SelectChild.SelectAnakAdapter

class ListAnakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListAnakBinding

    private lateinit var listAnakViewModel: ListAnakViewModel
    private lateinit var adapter: ListAnakAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()

        with(binding) {
            fabChild.setOnClickListener {
                startActivity(Intent(this@ListAnakActivity, AddAnakActivity::class.java))
            }

            adapter = ListAnakAdapter()
            rvChild.layoutManager = LinearLayoutManager(this@ListAnakActivity)
            rvChild.setHasFixedSize(true)
            rvChild.adapter = adapter

            listAnakViewModel = obtainViewModel(this@ListAnakActivity)

            listAnakViewModel.getAllChild().observe(this@ListAnakActivity) { childList ->
                if (childList != null) {
                    adapter.setListChild(childList)
                }
            }

            adapter.setOnItemClickCallback(object : ListAnakAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Child) {

                }
            })
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ListAnakViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ListAnakViewModel::class.java)
    }

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.tb_child)
        }
    }
}
