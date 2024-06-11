package ir.hirkancorp.presenter.core.firebaseMessaging.utils

object NotificationConstants {

    // Data payloads
    const val TYPE = "type"
    const val TYPE_CANCEL_REQUEST = "cancelRequest"
    const val TYPE_CANCEL_JOB = "cancelJob"
    const val CANCEL_JOB_REASON = "reason"
    const val CANCEL_JOB_REASON_BY_PROVIDER = "canceledByProvider"
    const val CANCEL_JOB_REASON_NO_RESPONSE_FROM_PROVIDER = "noResponseFromProvider"
    const val JOB_REQUEST_ID = "request_id"
    const val JOB_ID = "job_id"
    const val TYPE_JOB_PROGRESS = "jobProgress"
    const val JOB_PROGRESS_ACCEPTED = "accept_job_status_msg"
    const val JOB_PROGRESS_ACCEPTED_TIME = "accept_time"
    const val JOB_PROGRESS_ACCEPTED_STATUS = "accept_status"
    const val JOB_PROGRESS_ARRIVED = "arrive_job_status_msg"
    const val JOB_PROGRESS_ARRIVED_TIME = "arrive_time"
    const val JOB_PROGRESS_ARRIVED_STATUS = "arrive_status"
    const val JOB_PROGRESS_BEGIN_JOB = "begin_job_status_msg"
    const val JOB_PROGRESS_BEGIN_JOB_TIME = "begin_time"
    const val JOB_PROGRESS_BEGIN_JOB_STATUS = "begin_status"
    const val JOB_PROGRESS_ENDED = "end_job_status_msg"
    const val JOB_PROGRESS_ENDED_TIME = "end_time"
    const val JOB_PROGRESS_ENDED_STATUS = "end_status"

}