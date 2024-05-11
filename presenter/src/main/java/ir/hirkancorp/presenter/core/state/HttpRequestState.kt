package ir.hirkancorp.presenter.core.state


sealed class HttpRequestState<T> {
    data class ResponseState<T>(val data: T?) : HttpRequestState<T>()
    data class ErrorState<T>(val message: String?) : HttpRequestState<T>()
    class LoadingState<T> : HttpRequestState<T>()
}