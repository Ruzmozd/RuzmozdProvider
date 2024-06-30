package ir.hirkancorp.domain.requests.model

data class Service(
    val bookingType: String = "",
    val jobId: Int = 0,
    val jobLocation: String = "",
    val priceType: String = "",
    val promoId: Int = 0,
    val providerId: Int = 0,
    val requestId: Int = 0,
    val scheduleDate: String = "",
    val scheduleDisplayDate: String = "",
    val scheduleTime: String = "",
    val status: String = "",
    val userId: Int = 0
)

sealed class ServiceStatus(val status: String) {

    companion object {
        fun String.toStatus(): ServiceStatus {
            return when (this) {
                "Canceled" -> Canceled(statusValue = "لغو شده")
                "Completed" -> Completed(statusValue = "تکمبل شده")
                "Rating" -> Rating(statusValue = "ستاده دار")
                "Pending" -> Pending(statusValue = "معلق")
                "Scheduled" -> Scheduled(statusValue = "در انتظار")
                "Begin job" -> BeginJob(statusValue = "شروع شده")
                "End job" -> EndJob(statusValue = "نکمیل شده")
                "Payment" -> Payment(statusValue = "پرداخت شده")
                else -> Canceled(statusValue = "لغو شده")
            }
        }
    }


    data class Canceled(val statusValue: String): ServiceStatus(status = statusValue)
    data class Completed(val statusValue: String): ServiceStatus(status = statusValue)
    data class Rating(val statusValue: String): ServiceStatus(status = statusValue)
    data class Pending(val statusValue: String): ServiceStatus(status = statusValue)
    data class Scheduled(val statusValue: String): ServiceStatus(status = statusValue)
    data class BeginJob(val statusValue: String): ServiceStatus(status = statusValue)
    data class EndJob(val statusValue: String): ServiceStatus(status = statusValue)
    data class Payment(val statusValue: String): ServiceStatus(status = statusValue)
}