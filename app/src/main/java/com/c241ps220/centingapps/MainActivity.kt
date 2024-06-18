package com.c241ps220.centingapps

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.WorkManager
import com.c241ps220.centingapps.ViewModelFactory.ViewModelFactory
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.databinding.ActivityMainBinding
import com.c241ps220.centingapps.databinding.ActivityZoomImageBinding
import com.c241ps220.centingapps.utils.ReminderWorker
import com.c241ps220.centingapps.views.Fragment.BerandaFragment.BerandaFragment
import com.c241ps220.centingapps.views.Fragment.FaqFragment.FaqFragment
import com.c241ps220.centingapps.views.Fragment.HistoryFragment.HistoryFragment
import com.c241ps220.centingapps.views.Fragment.ProfileFragment.ProfileFragment
import com.c241ps220.centingapps.views.Login.LoginActivity
import com.c241ps220.centingapps.views.Register.RegisterActivity
import com.c241ps220.centingapps.views.SplashScreen.SplashscreenViewModel
import me.ertugrul.lib.OnItemSelectedListener
import androidx.work.*
import java.util.Calendar
import java.util.concurrent.TimeUnit

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

        createNotificationChannel(this)
        scheduleWeeklyReminder(this)

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

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.title_reminder)
            val descriptionText = getString(R.string.message_reminder)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("weekly_reminder_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleWeeklyReminder(context: Context) {
        val workManager = WorkManager.getInstance(context)

        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val initialDelay = calculateInitialDelay()
        Log.d("SplashScreenActivity", "Initial Delay: $initialDelay ms")

        val weeklyWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(7, TimeUnit.DAYS)
            .setConstraints(constraint)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

//        val weeklyWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.MINUTES)
//            .setConstraints(constraint)
//            .setInitialDelay(1, TimeUnit.MINUTES)
//            .build()

        workManager.enqueueUniquePeriodicWork(
            "weekly_reminder",
            ExistingPeriodicWorkPolicy.REPLACE,
            weeklyWorkRequest
        )

        // Tambahkan logging untuk memeriksa status worker
        workManager.getWorkInfoByIdLiveData(weeklyWorkRequest.id).observeForever { workInfo ->
            if (workInfo != null) {
                Log.d("WorkManagerStatus", "Status: ${workInfo.state}")
                if (workInfo.state == WorkInfo.State.ENQUEUED) {
                    Log.d("WorkManagerStatus", "Work is enqueued")
                }
            }
        }
    }

    fun calculateInitialDelay(): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val now = Calendar.getInstance()
        if (calendar.before(now)) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
        }

        // Hitung delay dalam milidetik
        val initialDelay = calendar.timeInMillis - now.timeInMillis
        Log.d("SplashScreenActivity", "Calculated initial delay: $initialDelay ms")
        return initialDelay
    }
}