package com.example.monpfe.Notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.monpfe.MESSAGE_REMINDER
import com.example.monpfe.MESSAGE_REMINDER_ID
import com.example.monpfe.R

class SampleBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationHelper.createSampleDataNotification(
            context,
            "DockNock",
            intent.getStringExtra(MESSAGE_REMINDER),
            intent.getStringExtra(MESSAGE_REMINDER_ID),
            false)

    }
}