package ir.hirkancorp.domain.request.model

data class BookJob(
    val type: String = "",
    val bookingType: String = "",
    val requestId: Int = 0,
    val userName: String = "",
    val rating: Int = 0,
    val address: String = "",
    val distance: Int = 0,
    val serviceName: String = "",
    val totalFare: String = "",
    val fareType: String = "",
    val number: Int = 0,
)
