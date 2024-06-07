package com.c241ps220.centingapps.views.Fragment.ProfileFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.FragmentFaqBinding
import com.c241ps220.centingapps.databinding.FragmentProfileBinding
import com.c241ps220.centingapps.views.profile.ProfileActivity


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            divProfile.setOnClickListener{
                startActivity(
                    Intent(
                        this@ProfileFragment.requireContext(),
                        ProfileActivity::class.java
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Membersihkan binding
    }

}