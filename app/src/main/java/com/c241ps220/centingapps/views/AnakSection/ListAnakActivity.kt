package com.c241ps220.centingapps.views.AnakSection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.data.database.AppDatabase
import com.c241ps220.centingapps.data.database.child.ChildDao
import com.c241ps220.centingapps.databinding.ActivityListAnakBinding
import kotlinx.coroutines.launch

class ListAnakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListAnakBinding
    private lateinit var database: AppDatabase
    private lateinit var childDao: ChildDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        childDao = database.childDao()

        setupToolbar()

        with(binding) {
            fabChild.setOnClickListener {
                startActivity(Intent(this@ListAnakActivity, AddAnakActivity::class.java))
            }

            // Dummy, kalau sudah tidak kepake hapus aja
            divDummyList.setOnClickListener {
                startActivity(Intent(this@ListAnakActivity, DetailAnakActivity::class.java))
            }
        }

        setupChildList()
    }

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.tb_child)
        }
    }

    private fun setupChildList() {
        // Coroutine to load children from the database
        lifecycleScope.launch {
            val children = childDao.getAllChildren()
            // Update the UI with the list of children
            // For example:
            // adapter.submitList(children)
        }
    }
}
