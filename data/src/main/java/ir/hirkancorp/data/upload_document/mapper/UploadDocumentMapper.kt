package ir.hirkancorp.data.upload_document.mapper

import ir.hirkancorp.data.upload_document.model.UploadDocumentData
import ir.hirkancorp.domain.upload_document.model.UploadDocument

fun UploadDocumentData.toDomain() =
    UploadDocument(documentURL = documentURL, documentStatus = documentStatus)