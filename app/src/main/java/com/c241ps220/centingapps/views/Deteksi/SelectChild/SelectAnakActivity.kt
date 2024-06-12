package com.c241ps220.centingapps.views.Deteksi.SelectChild

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.databinding.ActivitySelectAnakBinding
import com.c241ps220.centingapps.views.AnakSection.AddAnak.AddAnakActivity

class SelectAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectAnakBinding

    private lateinit var adapter: SelectAnakAdapter

    private lateinit var selectAnakViewModel: SelectAnakViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        with(binding) {

            fabChild.setOnClickListener {
                startActivity(Intent(this@SelectAnakActivity, AddAnakActivity::class.java))
            }

            adapter = SelectAnakAdapter()
            rvChild.layoutManager = LinearLayoutManager(this@SelectAnakActivity)
            rvChild.setHasFixedSize(true)
            rvChild.adapter = adapter

            selectAnakViewModel = obtainViewModel(this@SelectAnakActivity)

            selectAnakViewModel.getAllChild().observe(this@SelectAnakActivity) { childList ->
                if (childList != null) {
                    adapter.setListChild(childList)
                }
            }

            adapter.setOnItemClickCallback(object : SelectAnakAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Child) {

                }
            })
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): SelectAnakViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(SelectAnakViewModel::class.java)
    }

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(R.string.select_child)
        }
    }

}