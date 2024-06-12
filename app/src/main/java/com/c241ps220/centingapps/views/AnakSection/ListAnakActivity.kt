package com.c241ps220.centingapps.views.AnakSection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.data.database.AppDatabase
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.database.child.ChildDao
import com.c241ps220.centingapps.databinding.ActivityListAnakBinding
import kotlinx.coroutines.launch

class ListAnakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListAnakBinding
    private lateinit var database: AppDatabase
    private lateinit var childDao: ChildDao
    private lateinit var adapter: ChildAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        childDao = database.childDao()

        setupToolbar()
        setupRecyclerView()

        with(binding) {
            fabChild.setOnClickListener {
                startActivity(Intent(this@ListAnakActivity, AddAnakActivity::class.java))
            }

            // Dummy, kalau sudah tidak kepake hapus aja
            divDummyList.setOnClickListener {
                startActivity(Intent(this@ListAnakActivity, DetailAnakActivity::class.java))
            }
        }

        loadChildren()
    }

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.tb_child)
        }
    }

    private fun setupRecyclerView() {
        adapter = ChildAdapter { child ->
            val intent = Intent(this, DetailAnakActivity::class.java).apply {
                putExtra("child_id", child.id)
            }
            startActivity(intent)
        }
        binding.rvListAnak.layoutManager = LinearLayoutManager(this)
        binding.rvListAnak.adapter = adapter
    }

    private fun loadChildren() {
        lifecycleScope.launch {
//            val children = childDao.getAllChild()
//            adapter.submitList(children)
        }
    }
}
