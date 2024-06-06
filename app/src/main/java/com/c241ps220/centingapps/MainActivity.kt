package com.c241ps220.centingapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.c241ps220.centingapps.databinding.ActivityMainBinding
import com.c241ps220.centingapps.databinding.ActivityZoomImageBinding
import com.c241ps220.centingapps.views.Fragment.BerandaFragment.BerandaFragment
import com.c241ps220.centingapps.views.Fragment.FaqFragment.FaqFragment
import com.c241ps220.centingapps.views.Fragment.HistoryFragment.HistoryFragment
import com.c241ps220.centingapps.views.Fragment.ProfileFragment.ProfileFragment
import me.ertugrul.lib.OnItemSelectedListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentFragment: Fragment? = null

    private var backPressedTime: Long = 0
    private val TIME_INTERVAL: Long = 2000 // Waktu interval antara dua tekanan tombol "back", dalam milidetik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){

            // Set default fragment to Home
            replaceFragment(BerandaFragment())

            bottomBar.setOnItemSelectListener(object : OnItemSelectedListener {
                override fun onItemSelect(pos: Int) {
                    when (pos) {
                        0 -> replaceFragment(BerandaFragment())
                        1 -> replaceFragment(HistoryFragment())
                        2 -> replaceFragment(FaqFragment())
                        3 -> replaceFragment(ProfileFragment())
                        else -> {
                            Toast.makeText(this@MainActivity, "Opps!, Halaman Belum Tersedia", Toast.LENGTH_SHORT).show()
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
}