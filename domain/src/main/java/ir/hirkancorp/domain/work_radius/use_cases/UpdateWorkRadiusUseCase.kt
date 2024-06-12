package ir.hirkancorp.domain.work_radius.use_cases

import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.domain.work_radius.repository.WorkRadiusRepository
import kotlinx.coroutines.flow.Flow

class UpdateWorkRadiusUseCase(private val workRadiusRepository: WorkRadiusRepository) {

    suspend operator fun invoke(radius: Int): Flow<ApiResult<String>> = workRadiusRepository.updateWorkRadius(radius = radius)

}