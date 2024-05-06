package ir.hirkancorp.domain.utils

sealed class ApiResult<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : ApiResult<T>()
    class Success<T>(data: T?) : ApiResult<T>(data = data)
    class Error<T>(message: String, data: T? = null) : ApiResult<T>(data, message)
}