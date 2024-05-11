package ir.hirkancorp.presenter.screens.upload_document

import android.net.Uri

sealed class UploadDocumentEvent {
  data object InitScreen:UploadDocumentEvent()
  data class SetImageUri (val imageUri: Uri,val imageBytes: List<Byte>):UploadDocumentEvent()
  data object SendDocumentEvent : UploadDocumentEvent()
}