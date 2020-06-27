package com.example.monpfe.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.monpfe.MainActivity
import com.example.monpfe.R

object NotificationHelper {
    /**
     * Sets up the notification channels for API 26+.
     * Note: This uses package name + channel name to create unique channelId's.
     *
     * @param context     application context
     * @param importance  importance level for the notificaiton channel
     * @param showBadge   whether the channel should have a notification badge
     * @param name        name for the notification channel
     * @param description description for the notification channel
     */


    /**
     * Helps issue the default application channels (package name + app name) notifications.
     * Note: this shows the use of [NotificationCompat.BigTextStyle] for expanded notifications.
     *
     * @param context    current application context
     * @param title      title for the notification
     * @param message    content text for the notification when it's not expanded
     * @param bigText    long form text for the expanded notification
     * @param autoCancel `true` or `false` for auto cancelling a notification.
     * if this is true, a [PendingIntent] is attached to the notification to
     * open the application.
     */
    fun createSampleDataNotification(
        context: Context, title: String, message: String,
        bigText: String, autoCancel: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId =
                "${context.packageName}"+ bigText
            val channel = NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_HIGH)
            channel.description = message
            channel.setShowBadge(true)

            // Register the channel with the system
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val channelId = "${context.packageName}" + bigText
        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.pilulebg)
            setContentTitle(title)
            setContentText(message)
            setAutoCancel(autoCancel)
            setStyle(NotificationCompat.BigTextStyle().bigText(message))
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(autoCancel)
            if (bigText === "1") {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, 5, intent, 0)
                setContentIntent(pendingIntent)
            }
        }

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, notificationBuilder.build())
    }
}