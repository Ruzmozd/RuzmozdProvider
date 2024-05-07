package ir.hirkancorp.presenter.screens.profile_image

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.domain.profile_image.use_cases.ProfileImageUseCase
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.utils.UiText.StringResource
import ir.hirkancorp.presenter.core.utils.or
import ir.hirkancorp.presenter.screens.profile_image.ProfileImageEvent.SendUploadImageEvent
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ProfileImageViewModel(private val profileImageUseCase: ProfileImageUseCase) : ViewModel(),
    KoinComponent {
    var state by mutableStateOf(ProfileImageState())
        private set


    fun onEvent(event: ProfileImageEvent) {
        when (event) {
            is SendUploadImageEvent -> sendUploadImageEvent(event.image)
        }
    }

    private fun sendUploadImageEvent(image: List<Byte>) {
        val imageBytes = image.toByteArray()
        viewModelScope.launch {
            profileImageUseCase.invoke(imageBytes).collect { result ->
                state = when (result) {
                    is ApiResult.Loading -> state.copy(
                        isLoading = true
                    )
                    is ApiResult.Success -> {
                        state.copy(
                            isLoading = false,
                            imageUrl = result.data.toString(),
                            success = StringResource(R.string.profile_image_upload_photo_success)
                        )
                    }
                    is ApiResult.Error -> {
                        state.copy(
                            isLoading = false,
                            imageUrl = result.data.toString(),
                            error = result.message or R.string.all_unknown_error
                        )
                    }

                }
            }
        }
    }

}