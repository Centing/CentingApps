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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.c241ps220.centingapps.MainActivity
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.databinding.FragmentProfileBinding
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.AnakSection.ListAnak.ListAnakActivity
import com.c241ps220.centingapps.views.Fragment.HistoryFragment.HistoryViewModel
import com.c241ps220.centingapps.views.ResetPassword.ResetPasswordActivity
import com.c241ps220.centingapps.views.Profile.ProfileActivity
import com.c241ps220.centingapps.views.Login.LoginActivity
import com.c241ps220.centingapps.views.Onboarding.OnBoardingActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference

    private lateinit var viewModel: ProfileViewModel

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
            viewModel = obtainViewModel(this@ProfileFragment.requireActivity() as AppCompatActivity)

            viewModel.getSession().observe(viewLifecycleOwner) { user ->
                if (!user.isLogin) {
                    tvNameProfile.text = getString(R.string.please_login)
                    tvNameProfile.setOnClickListener {
                        startActivity(
                            Intent(
                                this@ProfileFragment.requireContext() as AppCompatActivity,
                                LoginActivity::class.java
                            ))
                    }
                    tvEmail.visibility = View.GONE
                    divName.visibility = View.GONE

                    divProfile.setOnClickListener {
                        (requireActivity() as MainActivity).isLoggin(true)
                    }
                    divDataAnak.setOnClickListener {
                        (requireActivity() as MainActivity).isLoggin(true)
                    }
                    divChangePassword.setOnClickListener {
                        (requireActivity() as MainActivity).isLoggin(true)
                    }
                }
                else{
                    tvNameProfile.text = user.name
                    tvEmail.text = user.email
                    tvEmail.visibility = View.VISIBLE
                    divName.visibility = View.VISIBLE
                    tvInisial.text = CustomFunction.getInitials(user.name)

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
                }
            }




            divLanguage.setOnClickListener {
                showConfirmationSettingDialog()
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ProfileViewModel {
        val factory = ViewModelFactory.getInstance(
            activity.application,
            UserPreference.getInstance(activity.application)
        )
        return ViewModelProvider(activity, factory).get(ProfileViewModel::class.java)
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up binding
    }
}
