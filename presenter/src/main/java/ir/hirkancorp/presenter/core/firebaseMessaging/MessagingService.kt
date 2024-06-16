package ir.hirkancorp.presenter.core.firebaseMessaging


import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ir.hirkancorp.core.LoggerUtil
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.CANCEL_REASON_BY_USER
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.JOB_ID
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.JOB_REQUEST_ID
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.REASON
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE_BOOK_JOB
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE_CANCEL_JOB
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE_CANCEL_REQUEST
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

class MessagingService: FirebaseMessagingService(), KoinComponent {

    private val bookJobNotificationSharedFlowWrapper: NotificationSharedFlowWrapper<Int> =
        get(named(NOTIFICATION_STATE_BOOK_JOB))
    private val cancelJobNotificationSharedFlowWrapper: NotificationSharedFlowWrapper<Pair<Int, String>> =
        get(named(NOTIFICATION_STATE_CANCEL_JOB))
    private val cancelRequestNotificationSharedFlowWrapper: NotificationSharedFlowWrapper<Pair<Int, String>> =
        get(named(NOTIFICATION_STATE_CANCEL_REQUEST))

    private lateinit var scope: CoroutineScope

    override fun onCreate() {
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
        data[JOB_ID]?.let { jobId ->
            scope.launch { bookJobNotificationSharedFlowWrapper.emit(jobId.toInt()) }
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
        val stringReason = when (reason) {
            CANCEL_REASON_BY_USER -> getString(R.string.firebase_messaging_service_cancel_request_reason_by_user)
            else -> getString(R.string.firebase_messaging_service_cancel_request_reason_unknown)
        }
        data[JOB_REQUEST_ID]?.let { requestId ->
            scope.launch { cancelRequestNotificationSharedFlowWrapper.emit(requestId.toInt() to stringReason) }
        }
    }

}