package com.emad.networkinfo

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.*
import android.telephony.ims.ImsReasonInfo
import android.util.Log
import android.telephony.PhoneStateListener

@SuppressLint("MissingPermission", "NewApi")
class MyPhoneStateListener {

    companion object listener : PhoneStateListener() {
        private const val TAG = "TAG"
        lateinit var callback: NotificationUpdater
        lateinit var localList: List<SubscriptionInfo>

        fun addListener(context: Context, callback: NotificationUpdater): PhoneStateListener {
            this.callback = callback
            val localSubscriptionManager = SubscriptionManager.from(context)
//            var sim0 = localSubscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(0)
//            var sim1 = localSubscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(1)
//            Log.e(TAG, "addListener: \n" + sim0.toString() + "\n" + sim1.toString() )
//            localList = localSubscriptionManager.completeActiveSubscriptionInfoList
//            for(i in localList.indices){
//                val cellInfoModel = CellInfoModel()
//                cellInfoModel.operator = localList[i].displayName.toString()
//                cellInfoModel.technology = localList[i].number
//                cellInfoModel.mnc = localList[i].mnc
//                cellInfoModel.id = localList.size - i - 1
////                cellInfoModel.activated = localList[i]
//                listener.callback.updateNotification(cellInfoModel)
//            }
                return listener;
        }

        override fun onCellInfoChanged(cellInfo: MutableList<CellInfo>?) {
            super.onCellInfoChanged(cellInfo)
            if (cellInfo == null || cellInfo.size == 0)
                return
            for (i in 0 until cellInfo.size) {
                val cellInfoModel = CellInfoModel()
//                for(j in localList.indices){
//                    if((localList[j] as SubscriptionInfo).mncString == EnumConverter.takeInfo(cellInfo[i].cellIdentity.toString(), "mMnc")){
//                        cellInfoModel.operator = (localList[j] as SubscriptionInfo).displayName.toString()
////                        cellInfoModel.id = j
//                        }
//                }
                cellInfoModel.operator = EnumConverter.takeInfo(cellInfo[i].toString(), "mAlphaShort")
                if (cellInfoModel.operator.isNullOrBlank()) continue
                cellInfoModel.rsrq = EnumConverter.takeInfo(cellInfo[i].toString(), "rsrq")
                EnumConverter.takeInfo(cellInfo[i].toString(), "level")?.let{
                    cellInfoModel.level = EnumConverter.convertLevel(it)
                }
                cellInfoModel.connected = cellInfo[i].cellConnectionStatus > 0
                cellInfoModel.activated = cellInfo[i].isRegistered
                cellInfoModel.mnc = Integer.parseInt(EnumConverter.takeInfo(cellInfo[i].cellIdentity.toString(), "mMnc"))
                cellInfoModel.mEarfcn = Integer.parseInt(EnumConverter.takeInfo(cellInfo[i].cellIdentity.toString(), "mEarfcn"))
                callback.updateNotification(cellInfoModel)
            }
            Log.e("TAG", "onCellInfoChanged: " + cellInfo)
        }

        override fun onSignalStrengthsChanged(signalStrength: SignalStrength?) {
            super.onSignalStrengthsChanged(signalStrength)
            if (signalStrength == null || signalStrength.cellSignalStrengths.size == 0)
                return
            for (i in 0 until signalStrength.cellSignalStrengths.size) {
                val cellInfoModel = CellInfoModel()
                cellInfoModel.rssi = signalStrength.cellSignalStrengths[i].dbm.toString() + "dbm"
                cellInfoModel.activated = true
                cellInfoModel.id = i;
                callback.updateNotification(cellInfo = cellInfoModel)
            }
            Log.e("TAG", "onSignalStrengthsChanged: " + signalStrength.toString())
        }

        override fun onCellLocationChanged(location: CellLocation?) {
            super.onCellLocationChanged(location)
            Log.e("TAG", "onCellLocationChanged: " + location.toString())
        }

        override fun onServiceStateChanged(serviceState: ServiceState?) {
            super.onServiceStateChanged(serviceState)
            serviceState?.let {
                for (i in 0 until serviceState.networkRegistrationInfoList.size) {
                    val cellInfoModel = CellInfoModel()
                    cellInfoModel.technology =
                        EnumConverter.takeInfo(serviceState.toString(), "accessNetworkTechnology")
                    cellInfoModel.operator =
                        serviceState.networkRegistrationInfoList[i].cellIdentity?.operatorAlphaShort.toString()
                    cellInfoModel.connected =
                        serviceState.networkRegistrationInfoList[i].availableServices.contains(2)
                    cellInfoModel.activated = true
                    Log.e("TAG", "NEW CELL ID | SERVICE STATE: " + serviceState.networkRegistrationInfoList[i].availableServices)
                    if (!cellInfoModel.operator.isNullOrBlank())
                        callback.updateNotification(cellInfo = cellInfoModel)
                }
            }
        }

        override fun onMessageWaitingIndicatorChanged(mwi: Boolean) {
            super.onMessageWaitingIndicatorChanged(mwi)
            Log.e("TAG", "onMessageWaitingIndicatorChanged: " + mwi)
        }

        override fun onCallForwardingIndicatorChanged(cfi: Boolean) {
            super.onCallForwardingIndicatorChanged(cfi)
            Log.e(TAG, "onCallForwardingIndicatorChanged: " + cfi)
        }

        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            super.onCallStateChanged(state, phoneNumber)
            Log.e(TAG, "onCallStateChanged: " + phoneNumber)
        }

        override fun onDataConnectionStateChanged(state: Int) {
            super.onDataConnectionStateChanged(state)
            Log.e(
                TAG,
                "onDataConnectionStateChanged: " + EnumConverter.convertConnectionStateEnum(state)
            )
        }

        override fun onDataConnectionStateChanged(state: Int, networkType: Int) {
            super.onDataConnectionStateChanged(state, networkType)
            Log.e(
                TAG,
                "onDataConnectionStateChanged: " + EnumConverter.convertConnectionStateEnum(state) + " NETWORK TYPE:" + networkType
            )
        }

        override fun onDataActivity(direction: Int) {
            super.onDataActivity(direction)
            Log.e(TAG, "onDataActivity: " + EnumConverter.convertDataActivityEnum(direction))
            val cellInfoModel = CellInfoModel()
            cellInfoModel.connected = direction != 0
            cellInfoModel.activated = true
            callback.updateNotification(cellInfo = cellInfoModel)
        }

        override fun onCallDisconnectCauseChanged(
            disconnectCause: Int,
            preciseDisconnectCause: Int
        ) {
            super.onCallDisconnectCauseChanged(disconnectCause, preciseDisconnectCause)
            Log.e(
                TAG,
                "onCallDisconnectCauseChanged: " + disconnectCause + " " + preciseDisconnectCause
            )
        }

        override fun onImsCallDisconnectCauseChanged(imsReasonInfo: ImsReasonInfo) {
            super.onImsCallDisconnectCauseChanged(imsReasonInfo)
            Log.e(TAG, "onImsCallDisconnectCauseChanged: " + imsReasonInfo)
        }

        override fun onPreciseDataConnectionStateChanged(dataConnectionState: PreciseDataConnectionState) {
            super.onPreciseDataConnectionStateChanged(dataConnectionState)
            Log.e(TAG, "onPreciseDataConnectionStateChanged: " + dataConnectionState)
        }

        override fun onUserMobileDataStateChanged(enabled: Boolean) {
            super.onUserMobileDataStateChanged(enabled)
            Log.e(TAG, "onUserMobileDataStateChanged: " + enabled)
        }

        override fun onDisplayInfoChanged(telephonyDisplayInfo: TelephonyDisplayInfo) {
            super.onDisplayInfoChanged(telephonyDisplayInfo)
            Log.e(TAG, "onDisplayInfoChanged: " + telephonyDisplayInfo)
        }

//        override fun onEmergencyNumberListChanged(emergencyNumberList: MutableMap<Int, MutableList<EmergencyNumber>>) {
//            super.onEmergencyNumberListChanged(emergencyNumberList)
//            Log.e(TAG, "onEmergencyNumberListChanged: " + emergencyNumberList )
//        }

        override fun onActiveDataSubscriptionIdChanged(subId: Int) {
            super.onActiveDataSubscriptionIdChanged(subId)
            Log.e(TAG, "onActiveDataSubscriptionIdChanged: " + subId)
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
            Log.e(TAG, "onRegistrationFailed: " + causeCode)
        }

        override fun onBarringInfoChanged(barringInfo: BarringInfo) {
            super.onBarringInfoChanged(barringInfo)
            Log.e(TAG, "onBarringInfoChanged: " + barringInfo)
        }

        fun listenEvents(): Int {
            return LISTEN_CELL_INFO or
                    LISTEN_SIGNAL_STRENGTHS or
                    LISTEN_CELL_LOCATION or
                    LISTEN_SERVICE_STATE or
                    LISTEN_ACTIVE_DATA_SUBSCRIPTION_ID_CHANGE or
                    0x00002000 or //LISTEN_DATA_CONNECTION_REAL_TIME_INFO
                    0x00040000 or //LISTEN_DATA_ACTIVATION_STATE
                    LISTEN_DISPLAY_INFO_CHANGED or
//                    0x00000200 or //LISTEN_ALWAYS_REPORTED_SIGNAL_STRENGTH //CANNOT USE THIS
//                    LISTEN_BARRING_INFO //CANNOT USE THIS
//                    LISTEN_CALL_DISCONNECT_CAUSES //CANNOT USE THIS
                    LISTEN_CALL_STATE or
                    LISTEN_DATA_ACTIVITY or
                    LISTEN_DATA_CONNECTION_STATE or
                    LISTEN_USER_MOBILE_DATA_STATE
        }
    }
}