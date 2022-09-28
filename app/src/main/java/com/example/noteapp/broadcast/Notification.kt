package com.example.noteapp.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.noteapp.R

const val CHANNEL_ID = "Channel"
const val TITLE_EXTRA = "titleExtra"
const val MESSAGE_EXTRA = "messageExtra"
const val NOTE_ID = "noteId"

class Notification : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        // Create the notification to be shown
        val mBuilder = NotificationCompat.Builder(p0!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(p1?.getStringExtra(TITLE_EXTRA))
            .setContentText(p1?.getStringExtra(MESSAGE_EXTRA))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Get the Notification manager service
        val alarmManager = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = p1!!.getIntExtra(NOTE_ID, 1)
        // Show a notification
        alarmManager.notify(notificationID, mBuilder.build())
    }
}