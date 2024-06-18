package com.c241ps220.centingapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.databinding.ActivityMainBinding
import com.c241ps220.centingapps.databinding.ActivityZoomImageBinding
import com.c241ps220.centingapps.views.Fragment.BerandaFragment.BerandaFragment
import com.c241ps220.centingapps.views.Fragment.FaqFragment.FaqFragment
import com.c241ps220.centingapps.views.Fragment.HistoryFragment.HistoryFragment
import com.c241ps220.centingapps.views.Fragment.ProfileFragment.ProfileFragment
import com.c241ps220.centingapps.views.Login.LoginActivity
import com.c241ps220.centingapps.views.Register.RegisterActivity
import com.c241ps220.centingapps.views.SplashScreen.SplashscreenViewModel
import me.ertugrul.lib.OnItemSelectedListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentFragment: Fragment? = null

    private var backPressedTime: Long = 0
    private val TIME_INTERVAL: Long =
        2000 // Waktu interval antara dua tekanan tombol "back", dalam milidetik

    private lateinit var manActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            manActivityViewModel = obtainViewModel(this@MainActivity)

            // Set default fragment to Home
            replaceFragment(BerandaFragment())

            bottomBar.setOnItemSelectListener(object : OnItemSelectedListener {
                override fun onItemSelect(pos: Int) {
                    when (pos) {
                        0 -> replaceFragment(BerandaFragment())
                        1 -> {
                            manActivityViewModel.getSession()
                                .observe(this@MainActivity) { user ->
                                    if (user.isLogin) {
                                        replaceFragment(HistoryFragment())
                                    }else{
                                        bottomBar.setOnItemSelectListener(this)
                                        isLoggin(true)
                                    }
                                }
                        }

                        2 -> replaceFragment(FaqFragment())
                        3 -> replaceFragment(ProfileFragment())
                        else -> {
                            Toast.makeText(
                                this@MainActivity,
                                "Opps!, Halaman Belum Tersedia",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
        currentFragment = fragment
    }

    override fun onBackPressed() {
        if (backPressedTime + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, getString(R.string.confirm_close), Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainActivityViewModel {
        val factory = ViewModelFactory.getInstance(
            activity.application,
            UserPreference.getInstance(application)
        )
        return ViewModelProvider(activity, factory).get(MainActivityViewModel::class.java)
    }

    fun isLoggin(state: Boolean) {
        with(binding) {
            if (state) {
                divStateLogin.root.visibility = View.VISIBLE
            } else {
                divStateLogin.root.visibility = View.GONE
            }

            divStateLogin.btLoginNow.setOnClickListener {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }

            divStateLogin.btCancel.setOnClickListener {
                isLoggin(false)
            }
        }
    }
}