package ir.hirkancorp.presenter.screens.requests

import androidx.paging.PagingData
import ir.hirkancorp.domain.requests.model.Service
import kotlinx.coroutines.flow.Flow

data class RequestsScreenState(
    val requests: Flow<PagingData<Service>>? = null
)
