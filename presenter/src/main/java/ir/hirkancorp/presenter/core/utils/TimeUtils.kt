package ir.hirkancorp.presenter.core.utils

import java.util.Locale
import java.util.concurrent.TimeUnit

object TimeUtils {
    fun Long.toTimeFormat(): String {
        val hours = TimeUnit.MILLISECONDS.toHours(this)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(this - TimeUnit.HOURS.toMillis(hours))
        val seconds = TimeUnit.MILLISECONDS.toSeconds(this - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes))
        return String.format(Locale(Locale.US.language), "%02d:%02d", minutes, seconds)

    }
}

fun Int.calculateRemainingTime(): Long {
    val givenTimeInMillis = this
    val leftTimeInMillis = System.currentTimeMillis() - givenTimeInMillis
    return 90_000L - leftTimeInMillis
}