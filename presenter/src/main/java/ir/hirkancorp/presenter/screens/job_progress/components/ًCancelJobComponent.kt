package ir.hirkancorp.presenter.screens.job_progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.hirkancorp.domain.job_progress.model.CancelReason
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.components.ButtonWithProgressIndicator
import ir.hirkancorp.presenter.core.components.TextInput
import ir.hirkancorp.presenter.core.state.HttpRequestState
import kotlin.random.Random

@Composable
fun CancelJobComponent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    reasons: HttpRequestState<List<CancelReason>>,
    errorLoadingReasons: (errorMessage: String?) -> Unit,
    onSubmitClick: (reasonId: Int, comment: String) -> Unit
) {

    var reasonId by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }

    Surface(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(3.dp)
                    .width(48.dp)
                    .background(MaterialTheme.colors.onSurface)
            )

            Spacer(modifier = Modifier.height(24.dp))
            when(reasons) {
                is HttpRequestState.LoadingState -> {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 64.dp, vertical = 16.dp)
                            .height(20.dp),
                        backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = .2f)
                    ) {}

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        for (i in 1..3) {
                            Card(
                                modifier = Modifier
                                    .width(Random.nextInt(from = 120, until = 260).dp)
                                    .padding(vertical = 16.dp)
                                    .height(16.dp),
                                backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = .2f)
                            ) {}
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .height(55.dp),
                        backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = .1f)
                    ) {}

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .height(55.dp),
                        backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = .2f)
                    ) {}
                }
                is HttpRequestState.ErrorState -> errorLoadingReasons(reasons.message)
                is HttpRequestState.ResponseState -> {
                    Text(
                        text = stringResource(R.string.job_progress_screen_cancel_reason_sheet_title),
                        style = MaterialTheme.typography.h6
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {

                        reasons.data?.forEach { reason ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = reason.id == reasonId,
                                    onClick = {
                                        reasonId = reason.id
                                    }
                                )
                                Text(text = reason.reason)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextInput(
                        placeholder = stringResource(R.string.job_progress_screen_write_your_comment),
                        lines = 3,
                        currentValue = comment,
                        onValueChange = { comment = it }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ButtonWithProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.all_submit),
                        isLoading = isLoading
                    ) {
                        if (reasonId > 0)  onSubmitClick(reasonId, comment)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }
    }
}