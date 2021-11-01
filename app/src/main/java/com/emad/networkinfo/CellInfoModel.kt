package com.emad.networkinfo

import java.io.Serializable

class CellInfoModel : Serializable {

    fun updateInfo(cellInfo: CellInfoModel) {
        if(!cellInfo.operator.isNullOrBlank())
            this.operator = cellInfo.operator
        if(!cellInfo.rssi.isNullOrBlank())
            this.rssi = cellInfo.rssi
        if(!cellInfo.rscp.isNullOrBlank())
            this.rscp = cellInfo.rscp
        if(!cellInfo.technology.isNullOrBlank())
            this.technology = cellInfo.technology
        if(cellInfo.connected != null)
            this.connected = cellInfo.connected
    }

    var operator: String? = null
    var rssi: String? = null //Signal Strength
    var rscp: String? = null //Signal Power
    var technology: String? = null //Connection Technology
    var connected: Boolean? = null
}