package ir.hirkancorp.presenter.core.utils

sealed class UiEvent {
    data class Navigate(val route: String, val args: Any = mutableListOf<Any>()): UiEvent()
    data object NavigateUp: UiEvent()
    data class ShowSnackBar(val message: String): UiEvent()

}
