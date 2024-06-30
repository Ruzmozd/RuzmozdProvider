package ir.hirkancorp.presenter.screens.requests

sealed class RequestsScreenEvent {

    data object LoadRequests: RequestsScreenEvent()

}