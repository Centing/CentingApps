package com.c241ps220.centingapps.views.Fragment.HistoryFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps220.centingapps.MainActivity
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.databinding.FragmentHistoryBinding
import com.c241ps220.centingapps.databinding.FragmentProfileBinding
import com.c241ps220.centingapps.views.AnakSection.DetailAnak.DetailAnakActivity
import com.c241ps220.centingapps.views.AnakSection.ListAnak.ListAnakAdapter
import com.c241ps220.centingapps.views.AnakSection.ListAnak.ListAnakViewModel
import com.c241ps220.centingapps.views.History.HistoryActivity


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            adapter = HistoryAdapter()
            rvChild.layoutManager = LinearLayoutManager(this@HistoryFragment.requireContext())
            rvChild.setHasFixedSize(true)
            rvChild.adapter = adapter

            viewModel = obtainViewModel(this@HistoryFragment.requireActivity() as AppCompatActivity)

            viewModel.getAllChild().observe(viewLifecycleOwner) { childList ->
                if (childList.size > 0) {
                    adapter.setListChild(childList)
                    rvChild.visibility = View.VISIBLE
                    tvEmpty.visibility = View.GONE
                } else {
                    rvChild.visibility = View.GONE
                    tvEmpty.visibility = View.VISIBLE
                }
            }

//            viewModel.getSession().observe(viewLifecycleOwner) { user ->
//                if (!user.isLogin) {
//                    (requireActivity() as MainActivity).isLoggin(true)
//                }
//            }

            adapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Child) {
                    val intent: Intent = Intent(
                        this@HistoryFragment.requireContext() as AppCompatActivity,
                        HistoryActivity::class.java
                    )
                    intent.putExtra("DATA_CHILD", data)
                    startActivity(intent)
                }
            })
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): HistoryViewModel {
        val factory = ViewModelFactory.getInstance(
            activity.application,
            UserPreference.getInstance(activity.application)
        )
        return ViewModelProvider(activity, factory).get(HistoryViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Membersihkan binding
    }

}