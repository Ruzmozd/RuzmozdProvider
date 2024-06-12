package ir.hirkancorp.domain.work_radius.repository

import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface WorkRadiusRepository {

    suspend fun updateWorkRadius(radius: Int): Flow<ApiResult<String>>

}