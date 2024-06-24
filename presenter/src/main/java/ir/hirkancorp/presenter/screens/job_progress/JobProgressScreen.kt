package ir.hirkancorp.presenter.screens.job_progress

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.components.ErrorPage
import ir.hirkancorp.presenter.core.components.dialogs.RuzmozdDialog
import ir.hirkancorp.presenter.core.state.HttpRequestState
import ir.hirkancorp.presenter.screens.job_progress.components.ProgressItem
import ir.hirkancorp.presenter.screens.job_progress.components.RatingComponent
import ir.hirkancorp.presenter.screens.register.components.RuzmozdTopAppBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun JobProgressScreen(
    modifier: Modifier = Modifier,
    jobProgressScreenViewModel: JobProgressScreenViewModel = koinViewModel(),
    jobId: Int?,
    navigateToProvidersScreen: () -> Unit
) {

    val context = LocalContext.current
    val state = jobProgressScreenViewModel.state
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )
    
    LaunchedEffect(key1 = true) {
        jobId?.let { id ->
            jobProgressScreenViewModel.onEvent(JobProgressScreenEvent.GetJobProgress(id))
        }
    }
    
    LaunchedEffect(key1 = jobProgressScreenViewModel.navigateToHomeScreen) {
        jobProgressScreenViewModel.navigateToHomeScreen.collectLatest { ratingSuccess ->
            if (ratingSuccess.second) {
                sheetState.hide()
                Toast.makeText(context, ratingSuccess.first, Toast.LENGTH_SHORT ).show()
                navigateToProvidersScreen()
            }
        }
    }

    LaunchedEffect(key1 = state.jobProgress) {
        if (state.jobProgress is HttpRequestState.ResponseState && state.jobProgress.data?.none() == false && state.jobProgress.data[3].status) {
            jobProgressScreenViewModel.onEvent(JobProgressScreenEvent.JobCompleted(state.jobProgress.data[3].jobStatusMsg))
        }
    }

    if (state.showDialog) {
        when (state.dialog) {
            is JobProgressScreenDialog.JobCompleted -> RuzmozdDialog(
                title = state.dialog.message,
                content = stringResource(R.string.job_progress_screen_request_job_compeleted),
                submitButtonText = stringResource(id = R.string.all_submit),
                onConfirmation = {
                    jobProgressScreenViewModel.onEvent(JobProgressScreenEvent.HideDialog)
                    scope.launch { sheetState.show() }
                })

            is JobProgressScreenDialog.JobCanceled -> RuzmozdDialog(
                title = state.dialog.message,
                content = stringResource(R.string.job_progress_screen_job_canceled_by_provider),
                submitButtonText = stringResource(id = R.string.all_submit),
                onConfirmation = {
                    jobProgressScreenViewModel.onEvent(JobProgressScreenEvent.HideDialog)
                    navigateToProvidersScreen()
                })

            null -> {}
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            RatingComponent(
                isLoading = state.ratingLoading,
                rateError = state.ratingError
            ) { rate: Int, comment: String ->
                jobProgressScreenViewModel.onEvent(JobProgressScreenEvent.Rate(rate = rate, comment = comment))
            }
        }) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                RuzmozdTopAppBar(title = stringResource(R.string.job_progress_screen_title))
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                when (state.jobProgress) {
                    is HttpRequestState.ResponseState -> state.jobProgress.data?.forEachIndexed { index, jobState ->
                        if (index > 0) Spacer(
                            modifier = Modifier
                                .padding(start = 32.dp)
                                .width(1.dp)
                                .height(32.dp)
                                .background(if (jobState.status) MaterialTheme.colors.primary else Color.Transparent)
                        )
                        ProgressItem(title = jobState.jobStatusMsg, currentState = jobState.status)
                    }

                    is HttpRequestState.ErrorState -> ErrorPage(
                        errorMessage = state.jobProgress.message?.ifBlank { stringResource(R.string.all_error_loading_data) }
                            .orEmpty()
                    )

                    is HttpRequestState.LoadingState -> {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        ProgressItem(title = stringResource(R.string.job_progress_screen_request_accepted))
                        Spacer(
                            modifier = Modifier
                                .padding(start = 24.dp)
                                .width(1.dp)
                                .height(32.dp)
                                .background(MaterialTheme.colors.onSurface.copy(alpha = .03f))
                        )
                        ProgressItem(title = stringResource(R.string.job_progress_screen_provider_arrived))
                        Spacer(
                            modifier = Modifier
                                .padding(start = 24.dp)
                                .width(1.dp)
                                .height(32.dp)
                                .background(MaterialTheme.colors.onSurface.copy(alpha = .03f))
                        )
                        ProgressItem(title = stringResource(R.string.job_progress_screen_request_started_the_job))
                        Spacer(
                            modifier = Modifier
                                .padding(start = 24.dp)
                                .width(1.dp)
                                .height(32.dp)
                                .background(MaterialTheme.colors.onSurface.copy(alpha = .03f))
                        )
                        ProgressItem(title = stringResource(R.string.job_progress_screen_request_job_compeleted))
                    }
                }
            }
        }
    }



}
