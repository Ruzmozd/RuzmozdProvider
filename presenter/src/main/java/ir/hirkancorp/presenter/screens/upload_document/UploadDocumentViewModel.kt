package ir.hirkancorp.presenter.screens.upload_document

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.domain.upload_document.use_cases.GetTempNationalCodeUseCase
import ir.hirkancorp.domain.upload_document.use_cases.UploadDocumentUseCase
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.utils.UiText.StringResource
import ir.hirkancorp.presenter.core.utils.or
import ir.hirkancorp.presenter.screens.upload_document.UploadDocumentEvent.InitScreen
import ir.hirkancorp.presenter.screens.upload_document.UploadDocumentEvent.SendDocumentEvent
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class UploadDocumentViewModel(
    private val uploadDocumentUseCase: UploadDocumentUseCase,
    private val getTempNationalCodeUseCase: GetTempNationalCodeUseCase) : ViewModel(),
    KoinComponent {

    var state by mutableStateOf(UploadDocumentState())
        private set

    fun onEvent(event: UploadDocumentEvent) {
        when (event) {
            is SendDocumentEvent -> sendDocumentEvent(state.image)
            is UploadDocumentEvent.SetImageUri -> setImageUri(event.imageUri,event.imageBytes)
            InitScreen -> initScreen()
        }
    }

    private fun setImageUri(imageUri: Uri, imageBytes: List<Byte>) {
        state = state.copy(capturedImage = imageUri, image = imageBytes)
    }

    private fun initScreen() {
        viewModelScope.launch {
            val nationalCode = getTempNationalCodeUseCase()
            state = state.copy(nationalCode = nationalCode)
        }
    }

    private fun sendDocumentEvent(image: List<Byte>) {
        if(state.isLoading)
            return
        viewModelScope.launch {
            uploadDocumentUseCase.invoke(image.toByteArray()).collect { result ->
                state = when (result) {
                    is ApiResult.Loading -> state.copy(
                        isLoading = true
                    )
                    is ApiResult.Success -> {
                        state.copy(
                            isLoading = false,
                            success = StringResource(R.string.profile_image_upload_photo_success)
                        )
                    }
                    is ApiResult.Error -> {
                        state.copy(
                            isLoading = false,
                            error = result.message or R.string.all_unknown_error
                        )
                    }
                }

            }
        }
    }

}