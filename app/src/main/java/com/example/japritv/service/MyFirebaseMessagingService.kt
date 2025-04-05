package com.example.japritv.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.japritv.MainActivity
import com.example.japritv.R
import com.google.firebase.FirebaseException
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

            super.onMessageReceived(remoteMessage)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationId = 1
            val requestCode = 1

            val channelId = "Firebase Messaging ID"
            val channelName = "Firebase Messaging"
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                )
            }

            val intent = Intent(this, MainActivity::class.java)
            val pendingIntentFlag = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) 0 else PendingIntent.FLAG_IMMUTABLE
            val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, pendingIntentFlag)

            val notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                .setSmallIcon(R.drawable.japripay)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(notificationId, notification)


        // ...
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationToServer($token)")
    }

    private fun showNotification(title: String?, body: String?) {
        // TODO: Implement this method to show a notification
        Log.d(TAG, "showNotification($title, $body)")
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}