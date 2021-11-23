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
        fun convertLevel(i: String) : String{
            return when(i){
                "0" -> "خیلی ضعیف"
                "1" -> "ضعیف"
                "2" -> "متوسط"
                "4" -> "قوی"
                else -> "خیلی ضعیف"
            }
        }
        fun takeInfo(source: String, target: String): String? {
            if (source.isNullOrEmpty() || target.isNullOrEmpty() || !source.contains(target)) return "-1";
            return source.substring(
                source.indexOf(target) + target.length + 1,
                source.indexOf(" ", source.indexOf(target))
            )
        }
    }
}