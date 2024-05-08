package ir.hirkancorp.presenter.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.domain.register.model.UserRegister
import ir.hirkancorp.domain.register.use_cases.RegisterUserUseCase
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.utils.UiText.DynamicString
import ir.hirkancorp.presenter.core.utils.UiText.StringResource
import ir.hirkancorp.presenter.core.utils.or
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class RegisterViewModel(private val registerUserUseCase: RegisterUserUseCase) : ViewModel(),
    KoinComponent {

    var state by mutableStateOf(RegisterState())
        private set

    init {
        getCities()
    }


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.SendRegisterEvent -> sendRegisterEvent(event.phoneNumber)
            is RegisterEvent.UpdateCity -> updateCity(event.city)
            is RegisterEvent.UpdateLastName -> updateLatName(event.lastName)
            is RegisterEvent.UpdateName -> updateName(event.name)
            is RegisterEvent.UpdateNationalCode -> updateNationalCode(event.nationalCode)
            is RegisterEvent.UpdateReferralCode -> updateReferralCode(event.referralCode)
        }
    }

    private fun getCities() {
        viewModelScope.launch {
            registerUserUseCase.invoke().collect { result ->
                state = when (result) {
                    is ApiResult.Loading -> state

                    is ApiResult.Success -> {
                        state.copy(
                            cities = result.data.orEmpty()
                        )
                    }

                    is ApiResult.Error -> {
                        state.copy(
                            cities = emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun isUserFilled(): Boolean =
        isNameValid() && isLastNameValid() && isNationalCodeValid()

    private fun sendRegisterEvent(phoneNumber: String) {
        if (isUserFilled().not())
            return

        val user = UserRegister(
            phoneNumber = phoneNumber,
            firstName = state.name,
            lastName = state.lastName,
            city = state.city.first,
            nationalityCode = state.nationalCode,
            referralCode = state.referralCode,
            nationality = "iran"
        )

        viewModelScope.launch {
            registerUserUseCase.invoke(user).collect { result ->
                state = when (result) {
                    is ApiResult.Loading -> state.copy(
                        isLoading = true
                    )

                    is ApiResult.Success -> {
                        state.copy(
                            isLoading = false,
                            token = result.data?.token.orEmpty(),
                            registrationMessage = result.data?.statusMessage or R.string.register_viewModel_register_success
                        )
                    }

                    is ApiResult.Error -> {
                        state.copy(
                            isLoading = false,
                            registrationMessage = result.data?.statusMessage or R.string.register_viewModel_register_fail
                        )
                    }

                }
            }
        }
    }

    private fun updateName(name: String) {
        state = state.copy(
            name = name
        )
    }

    private fun updateLatName(lastName: String) {
        state = state.copy(
            lastName = lastName
        )
    }

    private fun updateCity(city: Pair<Int, String>) {
        state = state.copy(
            city = city
        )
    }

    private fun updateNationalCode(nationalCode: String) {
        state = state.copy(
            nationalCode = nationalCode
        )
    }

    private fun updateReferralCode(referralCode: String) {
        state = state.copy(
            referralCode = referralCode
        )
    }


    private fun isNameValid(): Boolean = when {
        state.name.isBlank() -> {
            state = state.copy(
                nameError = StringResource(R.string.register_viewModel_name_error)
            )
            false
        }

        else -> {
            state = state.copy(
                nameError = DynamicString("")
            )
            true
        }
    }

    private fun isLastNameValid(): Boolean = when {
        state.lastName.isBlank() -> {
            state = state.copy(
                lastNameError = StringResource(R.string.register_viewModel_lastName_error)
            )
            false
        }

        else -> {
            state = state.copy(
                lastNameError = DynamicString("")
            )
            true
        }
    }

    private fun isNationalCodeValid(): Boolean = when {
        state.nationalCode.length != 10 -> {
            state = state.copy(
                nationalCodeError = StringResource(R.string.register_viewModel_nationalityCode_error)
            )
            false
        }

        else -> {
            state = state.copy(
                nationalCodeError = DynamicString("")
            )
            true
        }
    }

}