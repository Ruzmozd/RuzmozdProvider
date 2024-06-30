package ir.hirkancorp.data.requests.repository

import androidx.paging.PagingData
import io.ktor.client.call.body
import ir.hirkancorp.data.common.api_utils.commonPaging
import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.requests.mapper.toDomain
import ir.hirkancorp.data.requests.model.RequestsData
import ir.hirkancorp.data.requests.remote.RequestsClient
import ir.hirkancorp.domain.requests.model.Service
import ir.hirkancorp.domain.requests.repository.RequestsRepository
import kotlinx.coroutines.flow.Flow

class RequestsRepositoryImpl(
    private val requestsClient: RequestsClient
): RequestsRepository {

    override fun getRequests(): Flow<PagingData<Service>> =
        commonPaging(
            httpResponse = { loadSize, currentKey ->
                requestsClient.getRequests(paginate = loadSize, page = currentKey)
            },
            converter = {response ->
                response.body<HttpResponseModel<RequestsData>>().data.serviceData.toDomain()
            }
        )

}