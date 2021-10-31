package com.emad.networkinfo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

class NotificationMaker {
    companion object {
        var channelId: String = ""
        fun makeNotification(context: Context): Notification {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && channelId == "")
                channelId = createNotificationChannel(
                    context = context,
                    "telephony_channel",
                    "Cell Info Channel"
                )

            val notificationBuilder = NotificationCompat.Builder(context, channelId)

            val notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(PRIORITY_MAX)
                .setContentText("Observing Cell Info")
                .setChannelId(channelId)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()

            return notification
        }

        fun makeNotification(context: Context, content: String): Notification {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && channelId == "")
                channelId = createNotificationChannel(
                    context = context,
                    "telephony_channel",
                    "Cell Info Channel"
                )

            val notificationBuilder = NotificationCompat.Builder(context, channelId)

            val notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(PRIORITY_MAX)
                .setChannelId(channelId)
                .setContentText(content)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()

            return notification
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun createNotificationChannel(
            context: Context,
            channelId: String,
            channelName: String
        ): String {
            val chan = NotificationChannel(
                channelId,
                channelName, NotificationManager.IMPORTANCE_NONE
            )
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val service =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(chan)
            return channelId
        }
    }
}