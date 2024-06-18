package com.c241ps220.centingapps.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.c241ps220.centingapps.MainActivity
import com.c241ps220.centingapps.R

class ReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Log.d("ReminderWorker", "Doing work: sending notification")
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        Log.d("ReminderWorker", "Sending Notification")
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Intent untuk membuka MainActivity
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, "weekly_reminder_channel")
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentTitle(applicationContext.getString(R.string.title_reminder))
            .setContentText(applicationContext.getString(R.string.message_reminder))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}