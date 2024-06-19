package com.c241ps220.centingapps.views.ResetPassword

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.data.retrofit.ApiConfig
import com.c241ps220.centingapps.data.retrofit.UserUpdatePasswordAuth.UserUpdatePasswordRequest
import com.c241ps220.centingapps.data.retrofit.UserUpdatePasswordAuth.UserUpdatePasswordResponse
import com.c241ps220.centingapps.databinding.ActivityProfileBinding
import com.c241ps220.centingapps.databinding.ActivityResetPasswordBinding
import com.c241ps220.centingapps.views.Login.LoginActivity
import com.c241ps220.centingapps.views.Profile.ProfileActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var resetPasswordViewModel: ResetPasswordViewModel
    private lateinit var userPreference: UserPreference


    private var idUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        userPreference = UserPreference.getInstance(this)
        resetPasswordViewModel = obtainViewModel(this@ResetPasswordActivity)

        resetPasswordViewModel.getSession().observe(this) { user ->
            if (user.isLogin == true) {
                idUser = user.id
                with(binding) {
                    btCancel.setOnClickListener { finish() }
                    btSave.setOnClickListener {
                        val oldPassword = oldPasswordEditText.text.toString().trim()
                        val newPassword = newPasswordEditText.text.toString().trim()
                        val confirmPassword = confirmPasswordEditText.text.toString().trim()

                        when {
                            oldPassword.isEmpty() -> {
                                oldPasswordEditText.error = "Password lama harus diisi"
                                oldPasswordEditText.requestFocus()
                            }

                            oldPassword.length < 8 -> {
                                oldPasswordEditText.error =
                                    "Password lama harus memiliki minimal 8 karakter"
                                oldPasswordEditText.requestFocus()
                            }

                            newPassword.isEmpty() -> {
                                newPasswordEditText.error = "Password baru harus diisi"
                                newPasswordEditText.requestFocus()
                            }

                            newPassword.length < 8 -> {
                                newPasswordEditText.error =
                                    "Password baru harus memiliki minimal 8 karakter"
                                newPasswordEditText.requestFocus()
                            }

                            confirmPassword.isEmpty() -> {
                                confirmPasswordEditText.error = "Konfirmasi password baru harus diisi"
                                confirmPasswordEditText.requestFocus()
                            }

                            oldPassword == newPassword -> {
                                newPasswordEditText.error = "Password baru harus berbeda dari password lama"
                                newPasswordEditText.requestFocus()
                            }

                            newPassword != confirmPassword -> {
                                confirmPasswordEditText.error =
                                    "Konfirmasi password tidak cocok dengan password baru"
                                confirmPasswordEditText.requestFocus()
                            }

                            else -> {
                                val builder = AlertDialog.Builder(this@ResetPasswordActivity)
                                builder.setTitle(R.string.title_confirm_update_password)
                                builder.setMessage(R.string.message_confirm_update_password)

                                builder.setPositiveButton(R.string.ya) { dialog: DialogInterface, which: Int ->
                                    val updatePasswordRequest = UserUpdatePasswordRequest(
                                        id_user = idUser,
                                        old_password = oldPassword,
                                        new_password = newPassword
                                    )
                                    binding.loadingProgressBar.visibility = View.VISIBLE
                                    ApiConfig.createApiService().updatePassword(updatePasswordRequest)
                                        .enqueue(object : Callback<UserUpdatePasswordResponse> {
                                            override fun onResponse(
                                                call: Call<UserUpdatePasswordResponse>,
                                                response: Response<UserUpdatePasswordResponse>
                                            ) {
                                                binding.loadingProgressBar.visibility = View.GONE
                                                if (response.isSuccessful) {
                                                    val updatePasswordResponse = response.body()
                                                    updatePasswordResponse?.let {
                                                        Toast.makeText(this@ResetPasswordActivity, it.message, Toast.LENGTH_LONG).show()
                                                        logout()
                                                    }
                                                } else {
                                                    val message = when (response.code()) {
                                                        404 -> "User not found"
                                                        401 -> "Old password is incorrect"
                                                        else -> "Update failed: ${response.message()}"
                                                    }
                                                    Toast.makeText(this@ResetPasswordActivity, message, Toast.LENGTH_LONG).show()
                                                }
                                            }

                                            override fun onFailure(call: Call<UserUpdatePasswordResponse>, t: Throwable) {
                                                binding.loadingProgressBar.visibility = View.GONE
                                                Toast.makeText(this@ResetPasswordActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        })
                                }

                                builder.setNegativeButton(R.string.tidak) { dialog: DialogInterface, which: Int ->
                                    dialog.dismiss()
                                }

                                val dialog = builder.create()
                                dialog.show()



                            }
                        }


                    }
                }

            }else{
                finish()
            }
        }


    }

    private fun obtainViewModel(activity: AppCompatActivity): ResetPasswordViewModel {
        val factory = ViewModelFactory.getInstance(
            activity.application,
            UserPreference.getInstance(application)
        )
        return ViewModelProvider(activity, factory).get(ResetPasswordViewModel::class.java)
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

    private fun setupToolbar() {
        with(binding.divToolbar) {
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getString(R.string.p_password)
        }
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