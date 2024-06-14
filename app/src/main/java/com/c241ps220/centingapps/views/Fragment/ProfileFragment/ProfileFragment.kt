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
import androidx.lifecycle.lifecycleScope
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.data.pref.UserModel
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.databinding.FragmentProfileBinding
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.AnakSection.ListAnak.ListAnakActivity
import com.c241ps220.centingapps.views.ResetPassword.ResetPasswordActivity
import com.c241ps220.centingapps.views.Profile.ProfileActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreference = UserPreference.getInstance(requireContext())
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

        with(binding) {
            tvInisial.text = CustomFunction.getInitials(getString(R.string.dummy_name))

            divProfile.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        ProfileActivity::class.java
                    )
                )
            }

            divDataAnak.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        ListAnakActivity::class.java
                    )
                )
            }

            divChangePassword.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        ResetPasswordActivity::class.java
                    )
                )
            }

            divLanguage.setOnClickListener {
                showConfirmationSettingDialog()
            }

            divLogout.setOnClickListener {
                showLogoutConfirmationDialog()
            }

            lifecycleScope.launch {
                val userSession = userPreference.getSession().first()

                val initials = when {
                    userSession.name.split(" ").size == 1 -> {
                        if (userSession.name.length >= 2) {
                            userSession.name.substring(0, 2).toUpperCase()
                        } else {
                            userSession.name.toUpperCase()
                        }
                    }
                    else -> {
                        val names = userSession.name.split(" ")
                        val firstInitial = names.firstOrNull()?.substring(0, 1) ?: ""
                        val lastInitial = names.lastOrNull()?.substring(0, 1) ?: ""
                        "$firstInitial$lastInitial".toUpperCase()
                    }
                }

                tvInisial.text = initials

                tvNameProfile.text = userSession.name
                tvEmail.text = userSession.email
            }
        }
    }

    private fun showConfirmationSettingDialog() {
        val builder = AlertDialog.Builder(requireContext())
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

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.konfirmasi_logout)
        builder.setMessage(R.string.dialog_logout_message)

        builder.setPositiveButton(R.string.ya) { dialog: DialogInterface, which: Int ->
            logoutUser()
        }

        builder.setNegativeButton(R.string.tidak) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun logoutUser() {
        lifecycleScope.launch {
            userPreference.logout()
        }
        // Handle any additional logout logic here, such as navigating to the login screen
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up binding
    }
}
