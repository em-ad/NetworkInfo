package com.emad.networkinfo

class EnumConverter {
    companion object{
        fun convertDataActivityEnum(i: Int) : String{
            return when(i){
                0 -> "DATA_ACTIVITY_NONE"
                1 -> "DATA_ACTIVITY_IN"
                2 -> "DATA_ACTIVITY_OUT"
                3 -> "DATA_ACTIVITY_INOUT"
                4 -> "DATA_ACTIVITY_DORMANT"
                else -> "DATA_ACTIVITY_NONE"
            }
        }
        fun convertConnectionStateEnum(i: Int) : String{
            return when(i){
                0 -> "DATA_DISCONNECTED"
                1 -> "DATA_CONNECTING"
                2 -> "DATA_CONNECTED"
                4 -> "DATA_DISCONNECTING"
                else -> "DATA_DISCONNECTED"
            }
        }
    }
}