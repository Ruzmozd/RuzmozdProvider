package ir.hirkancorp.presenter.screens.profile

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.domain.provider_profile.use_cases.ProviderProfileUseCase
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.presenter.R
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileScreenViewModel(
    private val providerProfileUseCase: ProviderProfileUseCase
): ViewModel(), KoinComponent {

    private val context: Context by inject()
    var state by mutableStateOf(ProfileScreenState())
        private set


    fun onEvent(event: ProfileScreenEvent): Unit = when(event) {
        is ProfileScreenEvent.SetEditMode -> setEditMode(event.inEditMode)
        is ProfileScreenEvent.UpdateFirstName -> updateFirstName(event.firstName)
        is ProfileScreenEvent.UpdateLastName -> updateLastName(event.lastName)
        is ProfileScreenEvent.UpdateImage -> updateImage(event.image)
        ProfileScreenEvent.UpdateProfile -> updateProfile()
    }

    private fun updateFirstName(firstName: String) {
        state = state.copy(
            firstName = firstName
        )
    }

    private fun updateLastName(lastName: String) {
        state = state.copy(
            lastName = lastName
        )
    }

    private fun updateImage(image: List<Byte>) {
        state = state.copy(
            image = image
        )
    }

    private fun setEditMode(inEditMode: Boolean) {
        state = state.copy(
            inEditMode = inEditMode
        )
    }

    private fun updateProfile() {
        if (state.inEditMode) {
            if (!isInputsValid()) return
        }
        viewModelScope.launch {
            providerProfileUseCase.invoke(
                firstName = state.firstName.ifEmpty { null },
                lastName = state.lastName.ifEmpty { null },
                profileImage =  state.image.ifEmpty { null }?.toByteArray()
            ).collect { result ->
                state = when (result) {
                    is ApiResult.Loading -> state.copy(isLoading = true)
                    is ApiResult.Success -> {
                        state.copy(
                            isLoading = false,
                            inEditMode = false,
                            firstName = result.data?.firstName ?: "",
                            lastName = result.data?.lastName ?: "",
                            imageUrl = result.data?.profileImage ?: "",
                            mobileNumber = result.data?.mobileNumber ?: "",
                            nationality = result.data?.nationality ?: "",
                        )
                    }

                    is ApiResult.Error -> {
                        state.copy(
                            isLoading = false,
                            profileUpdateMessage = result.message ?: ""
                        )
                    }
                }
            }
        }
    }

    private fun isInputsValid(): Boolean = isFirstNameValid() && isLastNameValid()

    private fun isFirstNameValid(): Boolean = when {
        state.firstName.isBlank() -> {
            state = state.copy(
                firstNameError = context.getString(R.string.register_viewModel_name_error)
            )
            false
        }

        else -> {
            state = state.copy(
                firstNameError = ""
            )
            true
        }
    }

    private fun isLastNameValid(): Boolean = when {
        state.lastName.isBlank() -> {
            state = state.copy(
                lastNameError = context.getString(R.string.register_viewModel_lastName_error)
            )
            false
        }

        else -> {
            state = state.copy(
                lastNameError = ""
            )
            true
        }
    }

}