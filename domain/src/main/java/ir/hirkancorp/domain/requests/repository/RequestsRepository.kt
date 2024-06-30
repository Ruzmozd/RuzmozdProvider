package ir.hirkancorp.domain.requests.repository

import androidx.paging.PagingData
import ir.hirkancorp.domain.requests.model.Service
import kotlinx.coroutines.flow.Flow

interface RequestsRepository {

    fun getRequests(): Flow<PagingData<Service>>

}