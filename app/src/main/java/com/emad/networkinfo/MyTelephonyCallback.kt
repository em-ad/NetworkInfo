package com.emad.networkinfo

import android.annotation.SuppressLint
import android.telephony.ServiceState
import android.telephony.TelephonyCallback
import android.util.Log

@SuppressLint("NewApi")
open class  MyTelephonyCallback : TelephonyCallback(), TelephonyCallback.ServiceStateListener {
    override fun onServiceStateChanged(p0: ServiceState) {
        Log.e("TAG", "onServiceStateChanged: " )
    }
}