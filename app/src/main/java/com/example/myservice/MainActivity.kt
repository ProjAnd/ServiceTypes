package com.example.myservice

import android.R.attr.text
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


     fun playMusic(view: View){
         //setAlert()
         var intent = Intent(this, MyService::class.java)
         if(Build.VERSION.SDK_INT<=26){
             startService(intent)
         }else{
             startForegroundService(intent)
         }
     }

    private fun setAlert() {
        val i: Int = "11".toString().toInt()
        val intent = Intent(this, MyService::class.java)
        val pendingIntent = PendingIntent.getService(
            this.applicationContext, 234324243, intent,  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
            else PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + i * 1000] = pendingIntent
        Toast.makeText(this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();

    }


}