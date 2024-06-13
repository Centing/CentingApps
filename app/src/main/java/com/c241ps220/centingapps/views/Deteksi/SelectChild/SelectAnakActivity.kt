package com.c241ps220.centingapps.views.Deteksi.SelectChild

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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
                    val builder = AlertDialog.Builder(this@SelectAnakActivity)
                    builder.setTitle(R.string.konfirmasi)
                    builder.setMessage(R.string.dialog_select_child_detection_message)

                    builder.setPositiveButton(R.string.ya) { dialog: DialogInterface, which: Int ->
                        val intent = Intent()
                        intent.putExtra("SELECTED_ANAK", data)
                        setResult(RESULT_OK, intent)
                        finish()
                    }

                    builder.setNegativeButton(R.string.tidak) { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }

                    val dialog = builder.create()
                    dialog.show()


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