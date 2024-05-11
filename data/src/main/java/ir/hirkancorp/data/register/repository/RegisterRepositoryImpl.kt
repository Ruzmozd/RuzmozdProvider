package ir.hirkancorp.data.register.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.common.static_data.TempDataRepository
import ir.hirkancorp.data.preferences.repository.LocalServiceRepository
import ir.hirkancorp.data.register.mapper.toDomain
import ir.hirkancorp.data.register.model.CityListData
import ir.hirkancorp.data.register.model.UserRegisterData
import ir.hirkancorp.data.register.remote.RegisterClient
import ir.hirkancorp.domain.register.model.City
import ir.hirkancorp.domain.register.model.UserRegister
import ir.hirkancorp.domain.register.model.UserRegisterResult
import ir.hirkancorp.domain.register.repository.RegisterRepository
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.domain.utils.Constants.TEMP_DATA_NATIONAL_CODE
import kotlinx.coroutines.flow.Flow


class RegisterRepositoryImpl(
    private val client: RegisterClient,
    private val localServiceRepository: LocalServiceRepository,
    private val tempDataRepository : TempDataRepository<String>
) : RegisterRepository {

    override suspend fun register(user: UserRegister): Flow<ApiResult<UserRegisterResult>> =
        commonRequest(
            httpResponse = { client.register(user) },
            errorCodes = listOf(Conflict, UnprocessableEntity),
            successAction = Pair(Created) { response ->
                val userRegisterResultData: HttpResponseModel<UserRegisterData> = response.body<HttpResponseModel<UserRegisterData>>()
                userRegisterResultData.data.let {
                    if (it.userId != null && it.userId > 0) {
                        saveAccessToken(it.accessToken)
                    }
                }
                userRegisterResultData.toDomain()
            }
        )

    override suspend fun getCities(): Flow<ApiResult<List<City>>> = commonRequest(
        httpResponse = { client.getCityList() },
        errorCodes = listOf(),
        successAction = Pair(OK) { result ->
            val cityListData = result.body<HttpResponseModel<CityListData>>()
            cityListData.data.cities.toDomain()
        }
    )

    override suspend fun tempSaveNationalCode(nationalCode: String) {
        tempDataRepository.saveData(TEMP_DATA_NATIONAL_CODE, nationalCode)
    }

    private suspend fun saveAccessToken(accessToken: String) {
        localServiceRepository.saveAccessToken(accessToken)
    }

}