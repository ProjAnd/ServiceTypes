package com.example.myservice

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


     fun playMusic(view: View){
         var intent = Intent(this, MyService::class.java)
         if(Build.VERSION.SDK_INT<26){
             startService(intent)
         }else{
             startForegroundService(intent)
         }


    }
}