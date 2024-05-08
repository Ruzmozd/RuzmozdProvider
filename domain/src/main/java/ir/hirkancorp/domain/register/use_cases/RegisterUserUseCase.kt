package ir.hirkancorp.domain.register.use_cases

import ir.hirkancorp.domain.register.model.City
import ir.hirkancorp.domain.register.model.UserRegister
import ir.hirkancorp.domain.register.model.UserRegisterResult
import ir.hirkancorp.domain.register.repository.RegisterRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class RegisterUserUseCase(private val registerRepository: RegisterRepository) {

    suspend operator fun invoke(user: UserRegister): Flow<ApiResult<UserRegisterResult>> = registerRepository.register(user)

    suspend operator fun invoke(): Flow<ApiResult<List<City>>> = registerRepository.getCities()

}