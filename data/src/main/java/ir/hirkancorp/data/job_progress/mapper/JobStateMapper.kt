package ir.hirkancorp.data.job_progress.mapper

import ir.hirkancorp.data.job_progress.model.JobProgressData
import ir.hirkancorp.data.job_progress.model.JobStateData
import ir.hirkancorp.domain.job_progress.model.JobProgress
import ir.hirkancorp.domain.job_progress.model.JobState

fun JobProgressData.toDomain(): JobProgress = JobProgress(jobProgress = jobProgress.map { it.toJobState() })

fun JobStateData.toJobState(): JobState = JobState(
    jobStatusMsg = jobStatusMsg,
    status = status,
    time = time
)