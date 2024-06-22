package ir.hirkancorp.presenter.core.firebaseMessaging


import android.app.NotificationManager
import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ir.hirkancorp.core.LoggerUtil
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
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.CANCEL_REASON_BY_USER
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.JOB_ID
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.JOB_REQUEST_ID
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.REASON
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE_BOOK_JOB
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE_CANCEL_JOB
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE_CANCEL_REQUEST
import ir.hirkancorp.presenter.core.utils.NotificationUtils
import ir.hirkancorp.presenter.core.utils.NotificationUtils.buildNotification
import ir.hirkancorp.presenter.core.utils.NotificationUtils.createNotificationChannel
import ir.hirkancorp.presenter.core.utils.NotificationUtils.requestPendingIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

class MessagingService: FirebaseMessagingService(), KoinComponent {

    private val bookJobNotificationSharedFlowWrapper: NotificationSharedFlowWrapper<Pair<BookJob?, String?>> =
        get(named(NOTIFICATION_STATE_BOOK_JOB))
    private val cancelJobNotificationSharedFlowWrapper: NotificationSharedFlowWrapper<Pair<Int, String>> =
        get(named(NOTIFICATION_STATE_CANCEL_JOB))
    private val cancelRequestNotificationSharedFlowWrapper: NotificationSharedFlowWrapper<Pair<Int, String>> =
        get(named(NOTIFICATION_STATE_CANCEL_REQUEST))

    private lateinit var scope: CoroutineScope
    private val notificationManager:  NotificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun onCreate() {
        createNotificationChannel()
        scope = CoroutineScope(Dispatchers.IO)
    }

    override fun onNewToken(token: String) {}

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            LoggerUtil.logI(::onMessageReceived.name, "NOTIFICATION BODY: ${it.body}")
        }
        message.data.let { data ->
            LoggerUtil.logI(::onMessageReceived.name, "DATA PAYLOAD: $data)")
            handleDataMessage(data)
        }
    }

    private fun handleDataMessage(data: Map<String, String>) {
        val type = data[TYPE]
        when(type) {
            TYPE_BOOK_JOB -> handleBookJob(data = data)
            TYPE_CANCEL_REQUEST -> handleCancelRequest(data = data)
            TYPE_CANCEL_JOB -> handleCancelJob(data = data)
        }

    }

    private fun handleBookJob(data: Map<String, String>) {
        data[JOB_REQUEST_ID]?.let { requestId ->
            val job = BookJob(
                type = data[TYPE].orEmpty(),
                bookingType = data[BOOK_TYPE].orEmpty(),
                requestId =  requestId.toInt(),
                userName = data[BOOKING_USER_NAME].orEmpty(),
                rating =  data[BOOKING_RATING].orEmpty().toInt(),
                address = data[BOOKING_ADDRESS].orEmpty(),
                distance = data[BOOKING_DISTANCE].orEmpty().toInt(),
                serviceName = data[BOOKING_SERVICE_NAME].orEmpty(),
                totalFare =  data[BOOKING_TOTAL_FARE].orEmpty(),
                fareType = data[BOOKING_FARE_TYPE].orEmpty(),
                number = data[BOOKING_NUMBER].orEmpty().toInt()
            )
            if (bookJobNotificationSharedFlowWrapper.hasSubscription()) {
                scope.launch { bookJobNotificationSharedFlowWrapper.emit(job to null) }
            } else {
                val notification = buildNotification(
                    title = getString(R.string.firebase_messaging_service_notification_title, data[BOOKING_TOTAL_FARE]),
                    content = data[BOOKING_ADDRESS].orEmpty(),
                    intent = requestPendingIntent(job)
                )
                notificationManager.notify(NotificationUtils.NOTIFICATION_ID, notification)
            }
        }
    }

    private fun handleCancelJob(data: Map<String, String>) {
        val reason = data[REASON].orEmpty()
        val stringReason = when (reason) {
            CANCEL_REASON_BY_USER -> getString(R.string.firebase_messaging_service_cancel_job_reason_by_user)
            else -> getString(R.string.firebase_messaging_service_cancel_job_reason_unknown)
        }
        data[JOB_ID]?.let { jobId ->
            scope.launch { cancelJobNotificationSharedFlowWrapper.emit(jobId.toInt() to stringReason) }
        }
    }

    private fun handleCancelRequest(data: Map<String, String>) {
        val reason = data[REASON].orEmpty()
        data[JOB_REQUEST_ID]?.let {
            scope.launch { bookJobNotificationSharedFlowWrapper.emit(null to reason) }
        }
    }

}