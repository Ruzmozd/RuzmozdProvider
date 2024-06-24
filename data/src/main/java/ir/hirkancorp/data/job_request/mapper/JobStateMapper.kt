package ir.hirkancorp.data.job_request.mapper

import ir.hirkancorp.data.job_request.model.JobProgressData
import ir.hirkancorp.data.job_request.model.JobStateData
import ir.hirkancorp.domain.job_request.model.JobProgress
import ir.hirkancorp.domain.job_request.model.JobState

fun JobProgressData.toDomain(): JobProgress = JobProgress(jobProgress = jobProgress.map { it.toJobState() })

fun JobStateData.toJobState(): JobState = JobState(
    jobStatusMsg = jobStatusMsg,
    status = status,
    time = time
)