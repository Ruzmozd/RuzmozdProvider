package ir.hirkancorp.presenter.core.utils

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
    data object NavigateUp: UiEvent()
    data class ShowSnackBar(val message: String): UiEvent()

}
