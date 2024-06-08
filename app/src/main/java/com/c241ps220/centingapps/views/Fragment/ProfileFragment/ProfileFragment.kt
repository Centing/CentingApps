package com.c241ps220.centingapps.views.Fragment.ProfileFragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.FragmentProfileBinding
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.AnakSection.ListAnakActivity
import com.c241ps220.centingapps.views.ResetPassword.ResetPasswordActivity
import com.c241ps220.centingapps.views.Profile.ProfileActivity


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
            tvInisial.text = CustomFunction.getInitials(getString(R.string.dummy_name))

            divProfile.setOnClickListener{
                startActivity(
                    Intent(
                        this@ProfileFragment.requireContext(),
                        ProfileActivity::class.java
                    )
                )
            }

            divDataAnak.setOnClickListener{
                startActivity(
                    Intent(
                        this@ProfileFragment.requireContext(),
                        ListAnakActivity::class.java
                    )
                )
            }

            divChangePassword.setOnClickListener{
                startActivity(
                    Intent(
                        this@ProfileFragment.requireContext(),
                        ResetPasswordActivity::class.java
                    )
                )
            }
            divLanguage.setOnClickListener {
                showConfirmationSettingDialog()
            }
        }
    }

    private fun showConfirmationSettingDialog() {
        val builder = AlertDialog.Builder(this@ProfileFragment.requireContext())
        builder.setTitle(R.string.konfirmasi)
        builder.setMessage(R.string.dialog_setting_message)

        builder.setPositiveButton(R.string.ya) { dialog: DialogInterface, which: Int ->
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        builder.setNegativeButton(R.string.tidak) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Membersihkan binding
    }

}