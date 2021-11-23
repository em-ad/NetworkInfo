package com.emad.networkinfo

import java.io.Serializable

class CellInfoModel() : Serializable {

    constructor(cellInfo: CellInfoModel) : this() {
        this.connected = cellInfo.connected
        this.rssi = cellInfo.rssi
        this.rscp = cellInfo.rscp
        this.id = cellInfo.id
        this.operator = cellInfo.operator
        this.technology = cellInfo.technology
        this.activated = cellInfo.activated
        this.mEarfcn = cellInfo.mEarfcn
        this.mnc = cellInfo.mnc
    }

    fun updateInfo(cellInfo: CellInfoModel) {
        if (!cellInfo.operator.isNullOrBlank())
            this.operator = cellInfo.operator
        if (!cellInfo.rssi.isNullOrBlank())
            this.rssi = cellInfo.rssi
        if (!cellInfo.rscp.isNullOrBlank())
            this.rscp = cellInfo.rscp
        if (!cellInfo.technology.isNullOrBlank())
            this.technology = cellInfo.technology
        if (cellInfo.connected != null)
            this.connected = cellInfo.connected
        if (cellInfo.activated != null)
            this.activated = cellInfo.activated
        if (cellInfo.mEarfcn != null)
            this.mEarfcn = cellInfo.mEarfcn
        if (cellInfo.mnc != null)
            this.mnc = cellInfo.mnc
    }

    fun isTheSameAs(cellInfo: CellInfoModel): Boolean {
        return ((cellInfo.operator != null && cellInfo.operator == this.operator) ||
                (cellInfo.id != null && cellInfo.id == this.id) ||
                (cellInfo.mnc != null && cellInfo.mnc == this.mnc))
    }

    var id: Int? = null
    var operator: String? = null
    var rssi: String? = null //Signal Strength
    var rscp: String? = null //Signal Power
    var technology: String? = null //Connection Technology
    var connected: Boolean? = null //Internet Connection
    var activated: Boolean? = null //Sim In Use for Data
    var mnc: Int? = null //Sim In Use for Data
    var mEarfcn: Int? = null
}