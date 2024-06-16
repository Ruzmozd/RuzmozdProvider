package ir.hirkancorp.presenter.core.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent

fun Context.restart() {
    val packageManager = packageManager
    val intent = packageManager.getLaunchIntentForPackage(packageName)!!
    val componentName = intent.component!!
    val restartIntent = Intent.makeRestartActivityTask(componentName)
    startActivity(restartIntent)
    Runtime.getRuntime().exit(0)
}


fun Context.findActivity(): Activity? =  when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}