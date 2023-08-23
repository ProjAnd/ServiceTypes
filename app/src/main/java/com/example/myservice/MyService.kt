package com.example.myservice

import android.app.Notification
import android.app.Notification.Action
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MyService : Service() {
    private val Notification_ID = "1200"
    private var isServiceRunning = false
    private val  channel_Id = "foreground Service"
    var player: MediaPlayer ?= null

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.file)
            .also {
                it.isLooping = true
                it.setVolume(400f, 400f)
            }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //player!!.start()
        startForgroundNotification()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        //player!!.stop()
        //player!!.release()
        isServiceRunning = false
        this.stopSelf()
    }

    fun startForgroundNotification(){
        if(isServiceRunning) return
        isServiceRunning = true

        var notifManager :NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        var intent = Intent(applicationContext, MainActivity::class.java)
        intent.also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            it.action= Intent.ACTION_MAIN
        }

        var pendingIntent = PendingIntent.getActivity(this, 100, intent,
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        else PendingIntent.FLAG_UPDATE_CURRENT)
         //PendingIntent.FLAG_IMMUTABLE other applications receing pending intent cannot update the pending intent data
         //PendingIntent.FLAG_MUTABLE  other applications receing pending intent can update the pending intent data by merging in
            // pendingIntent.send()


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

        val notification = NotificationCompat.Builder(this, channel_Id)
            .apply {
                setContentTitle("Notification")
                setContentInfo("This notification is for Playing music " +
                        "in background when app is killed")
                setContentIntent(pendingIntent)
                setSmallIcon(R.drawable.ic_launcher_foreground)
            }.build()

         startForeground(1, notification) //starting from background thread
         //notifManager.notify(1, notification) // starting notification from main thread
    }

}