package com.example.myservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class BindServiceActivity : AppCompatActivity() {
    lateinit var textView :TextView
    lateinit var bindService :BindService
    lateinit var binder :BindService.IBinder
    var isBound:Boolean = false

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
            val binder: BindService.IBinder = service as BindService.IBinder
            bindService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        textView = findViewById<TextView>(R.id.textView)
        var serviceIntent = Intent(this, BindService::class.java)
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun generateNumber(view: View){
        textView.text = Integer.toString(bindService.generateRandomNumber())
    }
}