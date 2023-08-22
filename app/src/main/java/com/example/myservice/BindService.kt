package com.example.myservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.Random

class BindService : Service() {
    var iBinder :Binder  =IBinder()
    var random = Random()
    override fun onBind(p0: Intent?): Binder {
        return iBinder
    }

    class IBinder : Binder() {
         fun getService():BindService{
             return  BindService()
         }
    }

    fun generateRandomNumber (): Int {
       return random.nextInt(200)
    }

}