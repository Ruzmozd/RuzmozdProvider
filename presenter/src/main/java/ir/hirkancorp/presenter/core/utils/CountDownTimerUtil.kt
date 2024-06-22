package ir.hirkancorp.presenter.core.utils


import android.os.CountDownTimer
import ir.hirkancorp.core.LoggerUtil
import ir.hirkancorp.presenter.core.utils.TimeUtils.toTimeFormat

object CountDownTimerUtil {

    private var timer: CountDownTimer? = null
    private const val MILLIS_INTERVAL = 1000L

    private fun getInstance(
        millisInFuture: Long,
        onTick: (tick: Long) -> Unit,
        onFinish: () -> Unit
    ): CountDownTimer = object : CountDownTimer(millisInFuture, MILLIS_INTERVAL) {
        override fun onTick(millisUntilFinished: Long) {
            onTick(millisUntilFinished)
            LoggerUtil.logI(::getInstance.name, millisUntilFinished.toTimeFormat())
        }

        override fun onFinish() {
            timer = null
            onFinish()
        }
    }

    fun startTimer(
        millisInFuture: Long,
        onTick: (tick: Long) -> Unit,
        onFinish: () -> Unit
    ) {
        if (timer == null) {
            timer = getInstance(
                millisInFuture = millisInFuture,
                onTick =  onTick,
                onFinish = onFinish
            )
        }
        timer?.start()
    }

    fun cancelTimer() {
        timer?.cancel()
    }

}