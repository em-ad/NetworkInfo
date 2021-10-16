package com.emad.networkinfo

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Typeface
import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emad.networkinfo.ui.theme.NetworkInfoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetworkInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color.LightGray) {
                    MainScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen() {

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


    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun DefaultPreview() {
    NetworkInfoTheme {
        Greeting("Android")
    }
}