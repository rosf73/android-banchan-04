package com.woowa.banchan.ui.screen

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.woowa.banchan.R
import com.woowa.banchan.ui.MainActivity
import java.security.SecureRandom

interface Notifications {
    fun showDeliveryCompleteNotification(
        context: Context,
        title: String,
        description: String,
        food: String,
        count: Int,
        id: Long,
        orderAt: Long,
        channelId: String,
        channelName: String
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(context.getString(R.string.order_id), id)
            putExtra(context.getString(R.string.order_at), orderAt)
        }

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                context,
                SecureRandom().nextInt(Int.MAX_VALUE),
                intent,
                FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(
                context,
                SecureRandom().nextInt(Int.MAX_VALUE),
                intent,
                FLAG_UPDATE_CURRENT
            )
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        makeChannel(
            notificationManager,
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )

        val notification =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_logo)
                .setColor(ContextCompat.getColor(context, R.color.primary_main))
                .setContentTitle(title)
                .setContentText(description)
                .setStyle(NotificationCompat.BigTextStyle().bigText(description))
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .build()
        makeNotification(notificationManager, notification)
    }
}

private fun makeChannel(
    notificationManager: NotificationManager,
    channelId: String,
    channelName: String,
    priority: Int
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(channelId, channelName, priority)
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.lightColor = R.color.primary_main
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(channel)
    }
}

private fun makeNotification(
    notificationManager: NotificationManager,
    notification: Notification
) {
    notificationManager.notify(SecureRandom().nextInt(Int.MAX_VALUE), notification)
}