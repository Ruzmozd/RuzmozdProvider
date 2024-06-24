package ir.hirkancorp.presenter.screens.job_progress.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme

@Composable
fun ProgressItem(
    modifier: Modifier = Modifier,
    title: String,
    currentState: Boolean = false
) {
    val stateIcon = if (currentState) R.drawable.all_check_filled else R.drawable.all_check
    val tint = if (currentState) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = .5f)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = ImageVector.vectorResource(id = stateIcon),
            contentDescription = "JobState",
            tint = tint
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = tint,
            fontWeight = if (currentState) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProgressItemPreview() {
    RuzmozdProviderTheme {
        Column {
            ProgressItem(title = stringResource(R.string.job_progress_screen_request_accepted), currentState = true)
            ProgressItem(title = stringResource(R.string.job_progress_screen_provider_arrived), currentState = true)
            ProgressItem(title = stringResource(R.string.job_progress_screen_request_started_the_job), currentState = false)
            ProgressItem(title = stringResource(R.string.job_progress_screen_request_job_compeleted), currentState = false)
        }
    }
}