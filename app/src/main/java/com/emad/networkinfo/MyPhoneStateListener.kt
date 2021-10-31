package com.emad.networkinfo

import android.annotation.SuppressLint
import android.telephony.*
import android.telephony.emergency.EmergencyNumber
import android.telephony.ims.ImsReasonInfo
import android.util.Log
import com.google.gson.Gson

@SuppressLint("MissingPermission")
class MyPhoneStateListener {
    companion object listener: PhoneStateListener() {
        private val TAG = "TAG"
        override fun onCellInfoChanged(cellInfo: MutableList<CellInfo>?) {
            super.onCellInfoChanged(cellInfo)
            Log.e("TAG", "onCellInfoChanged: " + cellInfo )
        }

        override fun onSignalStrengthsChanged(signalStrength: SignalStrength?) {
            super.onSignalStrengthsChanged(signalStrength)
            Log.e("TAG", "onSignalStrengthsChanged: " + Gson().toJson(signalStrength) )
        }

        override fun onCellLocationChanged(location: CellLocation?) {
            super.onCellLocationChanged(location)
            Log.e("TAG", "onCellLocationChanged: " + location )
        }

        override fun onServiceStateChanged(serviceState: ServiceState?) {
            super.onServiceStateChanged(serviceState)
            Log.e("TAG", "onServiceStateChanged: " + serviceState )
        }

        override fun onSignalStrengthChanged(asu: Int) {
            super.onSignalStrengthChanged(asu)
            Log.e("TAG", "onSignalStrengthChanged: " + asu )
        }

        override fun onMessageWaitingIndicatorChanged(mwi: Boolean) {
            super.onMessageWaitingIndicatorChanged(mwi)
            Log.e("TAG", "onMessageWaitingIndicatorChanged: " + mwi )
        }

        override fun onCallForwardingIndicatorChanged(cfi: Boolean) {
            super.onCallForwardingIndicatorChanged(cfi)
            Log.e(TAG, "onCallForwardingIndicatorChanged: " + cfi )
        }

        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            super.onCallStateChanged(state, phoneNumber)
            Log.e(TAG, "onCallStateChanged: " + phoneNumber )
        }

        override fun onDataConnectionStateChanged(state: Int) {
            super.onDataConnectionStateChanged(state)
            Log.e(TAG, "onDataConnectionStateChanged: " + state )
        }

        override fun onDataConnectionStateChanged(state: Int, networkType: Int) {
            super.onDataConnectionStateChanged(state, networkType)
            Log.e(TAG, "onDataConnectionStateChanged: " + state + " " + networkType )
        }

        override fun onDataActivity(direction: Int) {
            super.onDataActivity(direction)
            Log.e(TAG, "onDataActivity: " + direction )
        }

        override fun onCallDisconnectCauseChanged(disconnectCause: Int, preciseDisconnectCause: Int) {
            super.onCallDisconnectCauseChanged(disconnectCause, preciseDisconnectCause)
            Log.e(TAG, "onCallDisconnectCauseChanged: " + disconnectCause + " " + preciseDisconnectCause )
        }

        override fun onImsCallDisconnectCauseChanged(imsReasonInfo: ImsReasonInfo) {
            super.onImsCallDisconnectCauseChanged(imsReasonInfo)
            Log.e(TAG, "onImsCallDisconnectCauseChanged: " + imsReasonInfo )
        }

        override fun onPreciseDataConnectionStateChanged(dataConnectionState: PreciseDataConnectionState) {
            super.onPreciseDataConnectionStateChanged(dataConnectionState)
            Log.e(TAG, "onPreciseDataConnectionStateChanged: " + dataConnectionState)
        }

        override fun onUserMobileDataStateChanged(enabled: Boolean) {
            super.onUserMobileDataStateChanged(enabled)
            Log.e(TAG, "onUserMobileDataStateChanged: " + enabled )
        }

        override fun onDisplayInfoChanged(telephonyDisplayInfo: TelephonyDisplayInfo) {
            super.onDisplayInfoChanged(telephonyDisplayInfo)
            Log.e(TAG, "onDisplayInfoChanged: " + telephonyDisplayInfo )
        }

        override fun onEmergencyNumberListChanged(emergencyNumberList: MutableMap<Int, MutableList<EmergencyNumber>>) {
            super.onEmergencyNumberListChanged(emergencyNumberList)
            Log.e(TAG, "onEmergencyNumberListChanged: " + emergencyNumberList )
        }

        override fun onActiveDataSubscriptionIdChanged(subId: Int) {
            super.onActiveDataSubscriptionIdChanged(subId)
            Log.e(TAG, "onActiveDataSubscriptionIdChanged: " + subId )
        }

        override fun onRegistrationFailed(
            cellIdentity: CellIdentity,
            chosenPlmn: String,
            domain: Int,
            causeCode: Int,
            additionalCauseCode: Int
        ) {
            super.onRegistrationFailed(
                cellIdentity,
                chosenPlmn,
                domain,
                causeCode,
                additionalCauseCode
            )
            Log.e(TAG, "onRegistrationFailed: " + causeCode )
        }

        override fun onBarringInfoChanged(barringInfo: BarringInfo) {
            super.onBarringInfoChanged(barringInfo)
            Log.e(TAG, "onBarringInfoChanged: " + barringInfo )
        }

        fun listenEvents(): Int{
            return LISTEN_CELL_INFO or
                    LISTEN_SIGNAL_STRENGTH or
                    LISTEN_CELL_LOCATION or
                    LISTEN_SERVICE_STATE or
                    LISTEN_ACTIVE_DATA_SUBSCRIPTION_ID_CHANGE or
//                    LISTEN_BARRING_INFO //CANNOT USE THIS
//                    LISTEN_CALL_DISCONNECT_CAUSES //CANNOT USE THIS
                    LISTEN_CALL_STATE or
                    LISTEN_DATA_ACTIVITY or
                    LISTEN_DATA_CONNECTION_STATE or
                    LISTEN_USER_MOBILE_DATA_STATE
        }
    }
}