package com.emad.networkinfo

interface NotificationUpdater {
    fun updateNotification(cellInfo: CellInfoModel)
    fun askSimCount(): Int { return 1}
//    fun updateAllNotification(cellInfo: CellInfoModel)
}