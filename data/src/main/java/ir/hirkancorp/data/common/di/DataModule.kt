package ir.hirkancorp.data.common.di

import ir.hirkancorp.data.auth.di.authModule
import ir.hirkancorp.data.job_progress.di.jobProgressModule
import ir.hirkancorp.data.job_rating.di.jobRatingModule
import ir.hirkancorp.data.job_request.di.jobRequestModule
import ir.hirkancorp.data.login.di.loginModule
import ir.hirkancorp.data.logout.di.logOutModule
import ir.hirkancorp.data.preferences.di.preferencesModule
import ir.hirkancorp.data.profile_image.di.profileImageModule
import ir.hirkancorp.data.provider_location.di.providerLocationModule
import ir.hirkancorp.data.provider_profile.di.providerProfileModule
import ir.hirkancorp.data.provider_status.di.providerStatusModule
import ir.hirkancorp.data.register.di.registerModule
import ir.hirkancorp.data.requests.di.requestsModule
import ir.hirkancorp.data.update_device.di.updateDeviceModule
import ir.hirkancorp.data.upload_document.di.uploadDocumentModule
import ir.hirkancorp.data.work_radius.di.workRadiusModule
import org.koin.dsl.module

val dataModule = module {
    includes(
        httpClientModule,
        preferencesModule,
        loginModule,
        logOutModule,
        profileImageModule,
        registerModule,
        uploadDocumentModule,
        authModule,
        providerProfileModule,
        providerStatusModule,
        updateDeviceModule,
        workRadiusModule,
        providerLocationModule,
        jobRequestModule,
        jobProgressModule,
        jobRatingModule,
        requestsModule
    )
}