package com.emad.networkinfo

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TelephonyService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null //not supporting binder service
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

    }


}