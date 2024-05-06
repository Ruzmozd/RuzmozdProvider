package ir.hirkancorp.core

object DateUtils {

    fun Long.toTime(): String {
        val seconds = (this / 1000) % 60;
        val minutes = ((this / (1000 * 60)) % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

}