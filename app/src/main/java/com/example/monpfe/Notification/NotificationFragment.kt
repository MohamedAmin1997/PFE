package com.example.monpfe

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.legacy.content.WakefulBroadcastReceiver
import com.example.monpfe.Notification.SampleBootReceiver
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_notification.*
import java.text.SimpleDateFormat
import java.util.*

const val MESSAGE_REMINDER = "MessageREminder"
const val MESSAGE_REMINDER_ID = "ID"
class NotificationFragment: Fragment(){
    var medicTime = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_notification, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activatePushNotifLocal()
    }


    private fun activatePushNotifLocal() {
        val alarmMgr =activity?.getSystemService(ALARM_SERVICE) as AlarmManager

        val alarmIntent = Intent(activity, SampleBootReceiver::class.java).let {
            it.putExtra(MESSAGE_REMINDER, "C'est l'heure de prendre votre médicament")
            it.putExtra(
                MESSAGE_REMINDER_ID,
                "1"
            )
            PendingIntent.getBroadcast(activity, 0, it, 0)
        }


        val calendarCheck: Calendar = Calendar.getInstance().apply {
            val fakeDateNow = SimpleDateFormat("dd/MM/yyyy").format(Date())
            val t = medicTime
            var medicDateTimeSt = "$fakeDateNow $t"
            val sf = SimpleDateFormat("dd/MM/yyyy hh:mm")
            val medicDateTime = sf.parse(medicDateTimeSt)
            val nowDateTime = Date()
            timeInMillis =medicDateTime.time
            set(Calendar.HOUR,timeInMillis.toInt())
            set(Calendar.MINUTE, timeInMillis.toInt())

        }


        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendarCheck.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
        val alarmMgr2 = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmIntent2= Intent(activity, SampleBootReceiver::class.java).let {
            it.putExtra(MESSAGE_REMINDER, "C'est le jour de votre Rendez-vous")
            it.putExtra(
                MESSAGE_REMINDER_ID,
                "2")
            PendingIntent.getBroadcast(activity, 0, it, 0)
        }
        val calendarCheck2: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.YEAR,1)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.DAY_OF_WEEK,1)
        }
        alarmMgr2.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendarCheck2.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent2
        )

    }


}