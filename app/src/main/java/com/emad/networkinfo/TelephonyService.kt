package com.emad.networkinfo

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.google.gson.Gson

class TelephonyService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null //not supporting binder service
    }

    override fun onCreate() { //first thing called when service is started
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int { //second thing called after oncreate when service is called
        Log.e("TAG", "onStartCommand: " + Gson().toJson(intent) )
        this.startForeground(113, NotificationMaker.makeNotification(applicationContext))
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() { //end of service
        super.onDestroy()
    }
}