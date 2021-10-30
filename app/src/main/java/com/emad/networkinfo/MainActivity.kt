package com.emad.networkinfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emad.networkinfo.ui.theme.NetworkInfoTheme
import com.google.gson.Gson
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {

    var data: ArrayList<String> = ArrayList()
    lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extractCellData()
        serviceIntent = Intent(applicationContext, TelephonyService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else startService(serviceIntent)
        setContent {
            NetworkInfoTheme {
                Surface(color = Color.LightGray) {
                    displayListData(data = data)
                }
            }
        }
    }

    @SuppressLint("HardwareIds", "MissingPermission", "NewApi")
    private fun extractCellData() {
        val telephonyMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyMgr.allCellInfo?.let {
            for (i in 0 until it.size) {
                if (it[i].cellIdentity.operatorAlphaLong.isNullOrEmpty()) continue
                data.add("OPERATOR SLOT: " + (i + 1))
                data.add("operator: " + it[i].cellIdentity.operatorAlphaLong.toString())
                data.add("signal strength: " + it[i].cellSignalStrength.dbm + "dbm")
                data.add("signal level: " + it[i].cellSignalStrength.level)
                data.add("asu level: " + it[i].cellSignalStrength.asuLevel)
                data.add("rssi: " + takeInfo(it[i].toString(), "rssi"))
                data.add("rsrp: " + takeInfo(it[i].toString(), "rsrp"))
                data.add("rsrq: " + takeInfo(it[i].toString(), "rsrq"))
                data.add("rssnr: " + takeInfo(it[i].toString(), "rssnr"))
                data.add("cqi: " + takeInfo(it[i].toString(), "cqi"))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        stopService(serviceIntent)
    }
}

@Composable
fun displayListData(data: List<String>) {
    Column() {
        Text(
            text = stringResource(id = R.string.main_screen_title),
            modifier = Modifier
                .background(colorResource(id = R.color.grey), RoundedCornerShape(6.dp))
                .padding(all = 12.dp)
                .border(8.dp, Color.Transparent)
                .fillMaxWidth(),
            textAlign = TextAlign.Right,
            fontSize = 15.sp,
        )
        LazyColumn(modifier = Modifier.fillMaxSize(1F)) {
            items(data) { item ->
                DoubleCellInfoText(item.substring(0, item.indexOf(":")), item.substring(item.indexOf(":") + 1))
            }
        }
    }
}

@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
@Composable
fun MainScreen(telephonyMgr: TelephonyManager? = null) {

    Column(Modifier.border(width = 6.dp, Color.White)) {
        Text(
            text = stringResource(id = R.string.main_screen_title),
            modifier = Modifier
                .background(colorResource(id = R.color.grey), RoundedCornerShape(6.dp))
                .padding(all = 12.dp)
                .border(8.dp, Color.Transparent)
                .fillMaxWidth(),
            textAlign = TextAlign.Right,
            fontSize = 15.sp,
        )
        Spacer(modifier = Modifier.height(12.dp))
        telephonyMgr?.allCellInfo?.let {
            for (item in it) {
                if (item.cellIdentity.operatorAlphaLong.isNullOrEmpty()) continue
                CellInfoText("operator: " + item.cellIdentity.operatorAlphaLong.toString())
                CellInfoText("signal strength: " + item.cellSignalStrength.dbm + "dbm")
                CellInfoText("signal level: " + item.cellSignalStrength.level)
                CellInfoText("asu level: " + item.cellSignalStrength.asuLevel)
                CellInfoText("registered: " + item.isRegistered)
                CellInfoText("rssi: " + takeInfo(item.toString(), "rssi"))
                CellInfoText("rsrp: " + takeInfo(item.toString(), "rsrp"))
                CellInfoText("rsrq: " + takeInfo(item.toString(), "rsrq"))
                CellInfoText("rssnr: " + takeInfo(item.toString(), "rssnr"))
                CellInfoText("cqi: " + takeInfo(item.toString(), "cqi"))

                Spacer(
                    modifier = Modifier
                        .height(24.dp)
                        .background(color = Color.White)
                )
            }
        }
    }
}

fun takeInfo(source: String, target: String): String? {
    if (source.isNullOrEmpty() || target.isNullOrEmpty() || !source.contains(target)) return null;
    return source.substring(
        source.indexOf(target) + target.length + 1,
        source.indexOf(" ", source.indexOf(target))
    )
}

@Preview
@Composable
fun CellInfoText(info: String = "Cell Information") {
    Text(
        text = info.replace("null", "نامشخص"),
        modifier = Modifier
            .fillMaxWidth()
            .border(border = BorderStroke(6.dp, Color.LightGray))
            .padding(all = 8.dp)
            .background(colorResource(id = R.color.white), RoundedCornerShape(6.dp))
            .padding(all = 6.dp),
        textAlign = TextAlign.Start,
        fontSize = 14.sp,
    )
}

@Preview
@Composable
fun DoubleCellInfoText(info1: String = "Cell Info 1", info2: String = "Cell Info 2") {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = info1.replace("null", "نامشخص"),
            modifier = Modifier
                .border(border = BorderStroke(6.dp, Color.LightGray))
                .padding(all = 8.dp)
                .background(colorResource(id = R.color.white), RoundedCornerShape(6.dp))
                .padding(all = 6.dp),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
        )
        Text(
            text = info2.replace("null", "نامشخص"),
            modifier = Modifier
                .border(border = BorderStroke(6.dp, Color.LightGray))
                .padding(all = 8.dp)
                .background(colorResource(id = R.color.white), RoundedCornerShape(6.dp))
                .padding(all = 6.dp),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
        )
    }
}