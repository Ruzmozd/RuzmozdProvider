package ir.hirkancorp.domain.register.use_cases

import ir.hirkancorp.domain.register.repository.RegisterRepository

class TempSaveNationalCodeUseCase(private val registerRepository: RegisterRepository) {

    suspend operator fun invoke(code: String) = registerRepository.tempSaveNationalCode(code)

}