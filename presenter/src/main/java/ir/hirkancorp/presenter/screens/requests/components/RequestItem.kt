package ir.hirkancorp.presenter.screens.requests.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.user.ui.theme.RuzmozdUserTheme
import ir.hirkancorp.user.ui.theme.localAlertColors
import ir.hirkancrop.domain.requests.model.Service
import ir.hirkancrop.domain.requests.model.ServiceStatus
import ir.hirkancrop.domain.requests.model.ServiceStatus.Companion.toStatus

@Composable
fun RequestItem(
    modifier: Modifier = Modifier,
    item: Service
) {

    val alertsColors = localAlertColors.current
    val status = item.status.toStatus()
    val statusColor = when(status) {
        is ServiceStatus.BeginJob -> alertsColors.normal
        is ServiceStatus.Canceled -> alertsColors.error
        is ServiceStatus.Completed -> alertsColors.success
        is ServiceStatus.EndJob -> alertsColors.success
        is ServiceStatus.Payment -> alertsColors.success
        is ServiceStatus.Pending -> alertsColors.warning
        is ServiceStatus.Rating -> alertsColors.success
        is ServiceStatus.Scheduled -> alertsColors.normal
    }

    Surface(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )  {
                Text(
                    text = "${item.jobId}شماره درخواست: ",
                    style = MaterialTheme.typography.subtitle2
                )
                Card(
                    modifier = Modifier,
                    backgroundColor = statusColor.copy(alpha = .2f),
                    contentColor = statusColor,
                    elevation = 0.dp
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = status.status,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = item.scheduleDisplayDate,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                style = MaterialTheme.typography.caption,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "location"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = item.jobLocation,
                    style = MaterialTheme.typography.caption,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

val exampleService = Service(
    jobId = 34,
    jobLocation = "سهیل, شادمان, منطقه ۲ شهر تهران, شهر تهران, بخش مرکزی شهرستان تهران",
    scheduleDisplayDate = "1402/10/11 12:00",
    status = "Begin job"
)

@Composable
fun LoadingRequestItem() {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(16.dp),
                    backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = .2f),
                    elevation = 0.dp
                ) {}
                Card(
                    modifier = Modifier
                        .width(50.dp)
                        .height(16.dp),
                    backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = .2f),
                    elevation = 0.dp
                ) {}
            }

            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .height(16.dp),
                backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = .2f),
                elevation = 0.dp
            ) {}
        }
    }
}

@Preview
@Composable
private fun RequestItemPreview() {
    RuzmozdUserTheme {
        Column {
            RequestItem(
                item = exampleService
            )
            LoadingRequestItem()
        }
    }
}