package ir.hirkancorp.presenter.screens.register

import ir.hirkancorp.domain.register.model.City
import ir.hirkancorp.presenter.core.utils.UiText
import ir.hirkancorp.presenter.core.utils.UiText.*

data class RegisterState(
    val isLoading: Boolean = false,
    val cities: List<City> = emptyList(),
    val phoneNumber: String = "",
    val name: String = "",
    val nameError: UiText = DynamicString(""),
    val lastName: String = "",
    val lastNameError: UiText = DynamicString(""),
    val city: Pair<Int, String> = 0 to "",
    val cityError: UiText = DynamicString(""),
    val nationalCode: String = "",
    val nationalCodeError: UiText = DynamicString(""),
    val referralCode: String = "",
    val token: String = "",
    val registrationMessage: UiText = DynamicString("")
)
