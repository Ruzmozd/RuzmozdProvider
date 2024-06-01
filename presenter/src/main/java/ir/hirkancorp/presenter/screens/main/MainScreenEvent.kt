package ir.hirkancorp.presenter.screens.main

sealed class MainScreenEvent {

    data object CheckIfAuthenticate: MainScreenEvent()

}