package com.example.myservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MyBroadcastReceiver: BroadcastReceiver() {
    private val  channel_Id = "foreground Service"

    override fun onReceive(context: Context?, p1: Intent?) {
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();

        var notifManager : NotificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        var intent = Intent(context!!.applicationContext, MainActivity::class.java)
        intent.also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            it.action= Intent.ACTION_MAIN
        }

        var pendingIntent = PendingIntent.getActivity(context!!, 100, intent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
            else PendingIntent.FLAG_UPDATE_CURRENT)


        //android version greater than oreo version code 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notifiation Name"
            val descriptionText = "This notification is for Playing music in background even when app is killed"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channelId =
                NotificationChannel(channel_Id, name, importance).apply {
                    this.description = descriptionText
                }
            notifManager.createNotificationChannel(channelId)
        }

        val notification = NotificationCompat.Builder(context!!, channel_Id)
            .apply {
                setContentTitle("Notification")
                setContentInfo("This notification is for Playing music " +
                        "in background when app is killed")
                setContentIntent(pendingIntent)
                setSmallIcon(R.drawable.ic_launcher_foreground)
            }.build()

        notifManager.notify(1, notification)
    }
}