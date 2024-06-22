package ir.hirkancorp.presenter.core.firebaseMessaging.di

import ir.hirkancorp.domain.request.model.BookJob
import ir.hirkancorp.presenter.core.firebaseMessaging.NOTIFICATION_STATE_BOOK_JOB
import ir.hirkancorp.presenter.core.firebaseMessaging.NOTIFICATION_STATE_CANCEL_JOB
import ir.hirkancorp.presenter.core.firebaseMessaging.NotificationSharedFlowWrapper
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presenterUtilsModule = module {
    single(named(NOTIFICATION_STATE_BOOK_JOB)) { NotificationSharedFlowWrapper<Pair<BookJob?, String?>>() }
    single(named(NOTIFICATION_STATE_CANCEL_JOB)) { NotificationSharedFlowWrapper<Pair<Int, String>>() }
}