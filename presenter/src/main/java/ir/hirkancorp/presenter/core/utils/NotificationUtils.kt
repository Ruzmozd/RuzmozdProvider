package ir.hirkancorp.presenter.core.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ir.hirkancorp.domain.request.model.BookJob
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.BOOKING_ADDRESS
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.BOOKING_DISTANCE
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.BOOKING_FARE_TYPE
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.BOOKING_NUMBER
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.BOOKING_RATING
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.BOOKING_SERVICE_NAME
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.BOOKING_TOTAL_FARE
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.BOOKING_USER_NAME
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.BOOK_TYPE

object NotificationUtils {

    private const val CHANNEL_ID = "pushNotification"
    const val NOTIFICATION_ID = 101

    fun Context.buildNotification(
        title: String,
        content: String,
        intent: PendingIntent?
    ): Notification = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(content)
        .setContentIntent(intent)
        .setAutoCancel(true)
        .setSmallIcon(R.drawable.all_location)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()


    fun Context.createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun Context.requestPendingIntent(job: BookJob): PendingIntent {
        val intent = Intent(this, Class.forName("ir.hirkancorp.provider.MainActivity")).apply {
            putExtra(BOOK_TYPE, job.bookingType)
            putExtra(BOOKING_USER_NAME, job.userName)
            putExtra(BOOKING_RATING, job.rating)
            putExtra(BOOKING_ADDRESS, job.address)
            putExtra(BOOKING_DISTANCE, job.distance)
            putExtra(BOOKING_SERVICE_NAME, job.serviceName)
            putExtra(BOOKING_TOTAL_FARE, job.totalFare)
            putExtra(BOOKING_FARE_TYPE, job.fareType)
            putExtra(BOOKING_NUMBER, job.number)
        }
        return PendingIntent.getActivity(this, NOTIFICATION_ID, intent, FLAG_IMMUTABLE or FLAG_CANCEL_CURRENT)
    }

}
