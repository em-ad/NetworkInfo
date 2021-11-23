package com.emad.networkinfo

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.telephony.*
import android.telephony.PhoneStateListener.*

import android.app.NotificationManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import com.google.gson.Gson


@SuppressLint("MissingPermission", "NewApi")
class TelephonyService : Service(), NotificationUpdater {

    private val TAG = "TAG"
    private val thread = Thread()
    private lateinit var currentCellInfo: CellInfoModel

    override fun onBind(p0: Intent?): IBinder? {
        return null //not supporting binder service
    }

    override fun onCreate() { //first thing called when service is started
        super.onCreate()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int { //second thing called after oncreate when service is called
        thread.run {
            this@TelephonyService.startForeground(
                113,
                NotificationMaker.makeNotification(applicationContext)
            )
            val telephonyMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyMgr.listen(
                MyPhoneStateListener.addListener(this@TelephonyService, this@TelephonyService),
                MyPhoneStateListener.listenEvents()
            )
            val mLocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val locationListener = LocationListener {
                currentCellInfo.latitude = it.latitude
                currentCellInfo.longitude = it.longitude
            }
            mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                100,
                0.001f,
                locationListener
            )
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() { //end of service
        super.onDestroy()
    }

//    override fun askSimCount(): Int {
//        thread.run {
//            return if (this@TelephonyService::currentCellInfo.isInitialized)
//                currentCellInfo.size
//            else 0
//        }
//    }

    override fun updateNotification(cellInfo: CellInfoModel) {
        thread.run {

            if (this@TelephonyService::currentCellInfo.isInitialized)
                Log.e(TAG, "updateNotification: " + Gson().toJson(cellInfo) + " among " + Gson().toJson(currentCellInfo))
                updateActivatedItem(cellInfo)
            val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.notify(
                113,
                NotificationMaker.makeNotification(applicationContext, currentCellInfo)
            )
        }
    }

    private fun updateActivatedItem(cellInfo: CellInfoModel) {
        thread.run {
            if (!this@TelephonyService::currentCellInfo.isInitialized) {
                currentCellInfo = cellInfo
//                currentCellInfo.add(cellInfo)
                return
            }
            if (currentCellInfo.activated != null && currentCellInfo.activated!!) {
                var info = CellInfoModel(currentCellInfo)
                info.updateInfo(cellInfo)
                currentCellInfo = info
            }
        }

    }

//    private fun updateCurrentList(cellInfo: CellInfoModel) {
////        if(cellInfo.id == null) return
//        thread.run {
//            if (!this@TelephonyService::currentCellInfo.isInitialized) {
//                currentCellInfo = ArrayList()
//                currentCellInfo.add(cellInfo)
//                return
//            }
//            var found = false
//            for (i in 0 until currentCellInfo.size) {
//                found = false
//                if (currentCellInfo[i].isTheSameAs(cellInfo)) {
//                    found = true
//                    val info = CellInfoModel(currentCellInfo[i])
//                    info.updateInfo(cellInfo)
//                    currentCellInfo.removeAt(i)
//                    currentCellInfo.add(i, info)
//                    break
//                }
//            }
//            if (!found && !cellInfo.operator.isNullOrBlank())
//                currentCellInfo.add(cellInfo)
//
//        }

//    }
}