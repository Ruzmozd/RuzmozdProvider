package ir.hirkancorp.core

import android.util.Log

object LoggerUtil {

    const val HTTP_CLIENT = "httpLog"

    fun logE(tag: String, message: String) {
        Log.e(tag, message)
    }

    fun logI(tag: String, message: String) {
        Log.i(tag, message)
    }

    fun logV(tag: String, message: String) {
        Log.v(tag, message)
    }

}