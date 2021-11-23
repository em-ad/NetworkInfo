package com.emad.networkinfo

interface NotificationUpdater {
    fun updateNotification(cellInfo: CellInfoModel)
    fun askSimCount(): Int
//    fun updateAllNotification(cellInfo: CellInfoModel)
}