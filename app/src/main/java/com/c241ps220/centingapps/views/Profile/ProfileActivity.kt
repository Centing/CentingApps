package com.c241ps220.centingapps.views.Profile

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.pref.UserModel
import com.c241ps220.centingapps.databinding.ActivityProfileBinding
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.data.retrofit.ApiConfig
import com.c241ps220.centingapps.data.retrofit.UserUpdateProfileAuth.UserUpdateProfileRequest
import com.c241ps220.centingapps.data.retrofit.UserUpdateProfileAuth.UserUpdateProfileResponse
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.Login.LoginActivity
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userPreference: UserPreference
    private lateinit var profileActivityViewModel: ProfileActivityViewModel
    private var isSelectedGender = "Laki-Laki"
    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileActivityViewModel = obtainViewModel(this@ProfileActivity)


        userPreference = UserPreference.getInstance(this)

        setupToolbar()

        profileActivityViewModel.getSession().observe(this) { user ->
            if (user.isLogin == true) {
                with(binding) {
                    val initials = CustomFunction.getInitials(user.name)
                    id = user.id
                    tvInisial.text = initials
                    tvName.text = user.name
                    tvEmail.text = user.email
                    tvNomor.text = user.phone
                    tvAddress.text = user.address
                    tvJenisKelamin.text = user.gender
                    tvTanggalLahir.text = user.birthDate

                    btEditProfile.setOnClickListener {
                        divEdit.visibility = View.VISIBLE
                        divButtonStateEdit.visibility = View.VISIBLE
                        divAwal.visibility = View.GONE
                        divButtonStateAwal.visibility = View.GONE

                        etPhone.setText(tvNomor.text)
                        etAddress.setText(tvAddress.text)
                        etBirthDate.setText(tvTanggalLahir.text)
                        etBirthDate.setOnClickListener { showDatePickerDialog() }
                        setupGender()

                        if (user.gender == "Laki-Laki") {
                            genderMale()
                            isSelectedGender = "Laki-Laki"
                        } else {
                            genderFemale()
                            isSelectedGender = "Perempuan"
                        }


                        btSubmit.setOnClickListener {
                            showSubmitConfirmationDialog()
                        }

                    }


                    btCancel.setOnClickListener {
                        divEdit.visibility = View.GONE
                        divButtonStateEdit.visibility = View.GONE
                        divAwal.visibility = View.VISIBLE
                        divButtonStateAwal.visibility = View.VISIBLE
                    }

                }
            } else {
                finish()
            }
        }

        binding.logoutbutton.setOnClickListener {
            showLogoutConfirmationDialog()
        }

    }

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.profile_title)
        }
    }

    private fun genderMale() {
        with(binding) {
            isSelectedGender = "Laki-Laki"
            divGenderLaki.setBackgroundResource(R.drawable.rectangle_stroke2)
            divGenderPerempuan.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
        }
    }

    private fun genderFemale() {
        with(binding) {
            isSelectedGender = "Perempuan"
            divGenderLaki.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
            divGenderPerempuan.setBackgroundResource(R.drawable.rectangle_stroke2)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ProfileActivityViewModel {
        val factory = ViewModelFactory.getInstance(
            activity.application,
            UserPreference.getInstance(application)
        )
        return ViewModelProvider(activity, factory).get(ProfileActivityViewModel::class.java)
    }

    private fun setupGender() {
        with(binding) {
            divGenderLaki.setOnClickListener {
                isSelectedGender = "Laki-Laki"
                divGenderLaki.setBackgroundResource(R.drawable.rectangle_stroke2)
                divGenderPerempuan.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
            }
            divGenderPerempuan.setOnClickListener {
                isSelectedGender = "Perempuan"
                divGenderLaki.setBackgroundResource(R.drawable.rectangle_stroke_transparent)
                divGenderPerempuan.setBackgroundResource(R.drawable.rectangle_stroke2)
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DatePickerDialogStyle,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                binding.etBirthDate.setText(dateFormat.format(selectedDate.time))
            },
            year, month, day
        )

        datePickerDialog.show()
    }


    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this@ProfileActivity)
        builder.setTitle(R.string.konfirmasi_logout)
        builder.setMessage(R.string.dialog_logout_message)

        builder.setPositiveButton(R.string.ya) { dialog: DialogInterface, which: Int ->
            logout()
        }

        builder.setNegativeButton(R.string.tidak) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showSubmitConfirmationDialog() {
        val builder = AlertDialog.Builder(this@ProfileActivity)
        builder.setTitle(R.string.title_confirm_update_profile)
        builder.setMessage(R.string.message_confirm_update_profile)

        builder.setPositiveButton(R.string.ya) { dialog: DialogInterface, which: Int ->
            updateProfile()
        }

        builder.setNegativeButton(R.string.tidak) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun updateProfile() {
        with(binding) {
            val phone = etPhone.text.toString()
            val address = etAddress.text.toString()
            val gender = isSelectedGender
            val birthDate = etBirthDate.text.toString()

            val updateProfileRequest = UserUpdateProfileRequest(
                id_user = id,
                phone = phone,
                address = address,
                gender = gender,
                birth_date = birthDate
            )

            binding.loadingProgressBar.visibility = View.VISIBLE
            ApiConfig.createApiService().updateProfile(updateProfileRequest)
                .enqueue(object : Callback<UserUpdateProfileResponse> {
                    override fun onResponse(
                        call: Call<UserUpdateProfileResponse>,
                        response: Response<UserUpdateProfileResponse>
                    ) {
                        binding.loadingProgressBar.visibility = View.GONE
                        if (response.isSuccessful) {
                            val updateProfileResponse = response.body()
                            updateProfileResponse?.let {
                                Toast.makeText(this@ProfileActivity, it.message, Toast.LENGTH_LONG)
                                    .show()
                                divEdit.visibility = View.GONE
                                divButtonStateEdit.visibility = View.GONE
                                divAwal.visibility = View.VISIBLE
                                divButtonStateAwal.visibility = View.VISIBLE

                                tvNomor.text = phone
                                tvAddress.text = address
                                tvJenisKelamin.text = gender
                                tvTanggalLahir.text = birthDate

                                val user = UserModel(
                                    id,
                                    tvName.text.toString(),
                                    tvEmail.text.toString(),
                                    phone ?: "-",
                                    address ?: "-",
                                    gender ?: "-",
                                    birthDate ?: "-",
                                )
                                profileActivityViewModel.saveSession(user)
                            }
                        } else {
                            if (response.code() == 404) {
                                Toast.makeText(
                                    this@ProfileActivity,
                                    "User not found",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@ProfileActivity,
                                    "Update failed: ${response.message()}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<UserUpdateProfileResponse>, t: Throwable) {
                        binding.loadingProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@ProfileActivity,
                            "Error: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

        }
    }

    private fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            userPreference.logout()
//            finish()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }

    fun isDevelopment(state: Boolean) {
        with(binding) {
            if (state) {
                divDevelopment.root.visibility = View.VISIBLE
            } else {
                divDevelopment.root.visibility = View.GONE
            }

            divDevelopment.btDismiss.setOnClickListener {
                isDevelopment(false)
            }
        }
    }
}
