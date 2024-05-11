package ir.hirkancorp.presenter.screens.upload_document

import android.net.Uri
import ir.hirkancorp.presenter.core.utils.UiText
import ir.hirkancorp.presenter.core.utils.UiText.*


data class UploadDocumentState(
    val isLoading: Boolean = false,
    val image: List<Byte> = emptyList(),
    val capturedImage: Uri? = null,
    val nationalCode: String = "",
    val success: UiText = DynamicString(""),
    val error: UiText = DynamicString(""),
)