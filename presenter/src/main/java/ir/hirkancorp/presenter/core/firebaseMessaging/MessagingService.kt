package ir.hirkancorp.presenter.core.firebaseMessaging


import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ir.hirkancorp.core.LoggerUtil
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent

class MessagingService: FirebaseMessagingService(), KoinComponent {

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

    }

}