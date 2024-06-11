package ir.hirkancorp.presenter.core.firebaseMessaging.utils

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ir.hirkancorp.core.LoggerUtil


object FirebaseUtils {
    fun getFirebaseToken(onToken: (token: String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            onToken(task.result)
            LoggerUtil.logE(::getFirebaseToken.name, task.result)
        })
    }

}
