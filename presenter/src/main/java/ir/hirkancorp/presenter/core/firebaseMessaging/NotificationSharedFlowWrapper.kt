package ir.hirkancorp.presenter.core.firebaseMessaging

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NotificationSharedFlowWrapper<T> {

    private var _message = MutableSharedFlow<T>(replay = 0)
    var message = _message.asSharedFlow()
        private set

    suspend fun emit(value: T) {
        _message.emit(value)
    }

}

// Qualifiers
// const val NOTIFICATION_STATE_CANCEL_REQUEST = "cancel_request"
