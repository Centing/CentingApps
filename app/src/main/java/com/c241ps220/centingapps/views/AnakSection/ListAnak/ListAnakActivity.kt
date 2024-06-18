package com.c241ps220.centingapps.views.AnakSection.ListAnak

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.databinding.ActivityListAnakBinding
import com.c241ps220.centingapps.views.AnakSection.AddAnak.AddAnakActivity
import com.c241ps220.centingapps.views.AnakSection.DetailAnak.DetailAnakActivity


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
                if (childList.size > 0) {
                    adapter.setListChild(childList)
                    rvChild.visibility = View.VISIBLE
                    tvEmpty.visibility = View.GONE
                }else {
                    rvChild.visibility = View.GONE
                    tvEmpty.visibility = View.VISIBLE
                }
            }

            adapter.setOnItemClickCallback(object : ListAnakAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Child) {
                    val intent: Intent = Intent(
                        this@ListAnakActivity,
                        DetailAnakActivity::class.java
                    )
                    intent.putExtra("DATA_CHILD", data)
                    startActivity(intent)
                }
            })
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ListAnakViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, UserPreference.getInstance(application))
        return ViewModelProvider(activity, factory).get(ListAnakViewModel::class.java)
    }

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.tb_child)
        }
    }
}
