package ir.hirkancorp.data.job_progress.mapper

import ir.hirkancorp.domain.job_progress.model.CancelJobReasons
import ir.hirkancorp.domain.job_progress.model.CancelReason
import ir.hirkancorp.data.job_progress.model.CancelJobReasonsData
import ir.hirkancorp.data.job_progress.model.CancelReasonData

fun CancelJobReasonsData.toDomain(): CancelJobReasons =
    CancelJobReasons(cancelJobReasons = cancelJobReasons.map { it.toDomain() })

fun CancelReasonData.toDomain(): CancelReason = CancelReason(
    cancelledBy = cancelledBy,
    id = id,
    masterServiceId = masterServiceId,
    reason = reason,
    status = status
)