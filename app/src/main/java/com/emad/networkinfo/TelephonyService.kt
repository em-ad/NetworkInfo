package com.emad.networkinfo

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.telephony.*
import android.telephony.PhoneStateListener.*
import android.telephony.emergency.EmergencyNumber
import android.telephony.ims.ImsReasonInfo
import android.util.Log
import com.google.gson.Gson
import java.util.concurrent.Executor

import java.security.Permission
import java.security.Permissions

@SuppressLint("MissingPermission", "NewApi")
class TelephonyService: Service() {

    private val TAG = "TAG"
    private val thread = Thread()

    override fun onBind(p0: Intent?): IBinder? {
        return null //not supporting binder service
    }

    override fun onCreate() { //first thing called when service is started
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int { //second thing called after oncreate when service is called
        thread.run { this@TelephonyService.startForeground(113, NotificationMaker.makeNotification(applicationContext)) }
        thread.run {
            val telephonyMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyMgr.listen(MyPhoneStateListener.listener, MyPhoneStateListener.listenEvents())
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() { //end of service
        super.onDestroy()
    }
}