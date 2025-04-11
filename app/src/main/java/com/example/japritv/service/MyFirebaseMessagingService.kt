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
    fun formatRupiah(amount: String?): String {
        return try {
            val number = amount?.toDoubleOrNull() ?: return "Rp0"
            val format = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("in", "ID"))
            format.format(number)
        } catch (e: Exception) {
            "Rp0"
        }
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "FCM Received!")
        Log.d(TAG, "FCM Data: ${remoteMessage.data}")
        Log.d(TAG, "FCM Notification Title: ${remoteMessage.notification?.title}")
        Log.d(TAG, "FCM Notification Body: ${remoteMessage.notification?.body}")

        // Ambil dari data, bukan notification
        val data = remoteMessage.data
        val title = data["status"] ?: "Notifikasi"
        val body = "Pembayaran sebesar ${formatRupiah(data["amount"])} dengan ref ${data["ref"]}"


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val channelId = "Firebase Messaging ID"
        val channelName = "Firebase Messaging"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingIntentFlag)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title) // ✅ Dari data["status"]
            .setContentText(body)   // ✅ Tampilkan info pembayaran
            .setSmallIcon(R.drawable.japripay)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(notificationId, notification)



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