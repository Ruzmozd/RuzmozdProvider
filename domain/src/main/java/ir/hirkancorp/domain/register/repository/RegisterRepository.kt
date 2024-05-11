package ir.hirkancorp.domain.register.repository

import ir.hirkancorp.domain.register.model.City
import ir.hirkancorp.domain.register.model.UserRegister
import ir.hirkancorp.domain.register.model.UserRegisterResult
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    suspend fun register(user: UserRegister): Flow<ApiResult<UserRegisterResult>>

    suspend fun getCities(): Flow<ApiResult<List<City>>>

    suspend fun tempSaveNationalCode(nationalCode:String)
}