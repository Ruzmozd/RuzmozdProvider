package ir.hirkancorp.presenter.core.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ir.hirkancorp.domain.request.model.BookJob
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.LocalSpacing
import ir.hirkancorp.presenter.core.components.ColoredButton
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme
import ir.hirkancorp.presenter.core.theme.localAlertColors


@Composable
fun RequestDialog(
    title: String,
    request: BookJob,
    icon: ImageVector = Icons.Default.Info,
    submitButtonText: String = stringResource(id = R.string.all_submit),
    onsubmit: () -> Unit,
    dismissButtonText: String = stringResource(id = R.string.all_dismis),
    onDismiss: () -> Unit,
    onDismissRequest: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    val localColors = localAlertColors.current

    val time = if (request.fareType == "PerHour") stringResource(id = R.string.request_dialog_hours, request.rating) else  stringResource(id = R.string.request_dialog_days, request.rating)

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnClickOutside = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = icon, contentDescription = "Dialog icon")
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Text(text = title, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        progress = .73f,
                        backgroundColor = MaterialTheme.colors.onBackground.copy(alpha = .1f)
                    )
                    Text(text = "00:37", style = MaterialTheme.typography.h5)
                }
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = request.userName,
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Center
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .width(1.dp)
                            .height(12.dp)
                            .background(MaterialTheme.colors.onSurface)
                    )
                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.request_dialog_rating, request.rating),
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = "دستمزد:",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier,
                        text = "${request.fare} تومان",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = "زمان:",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier,
                        text = time,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "آدرس: ${request.address}",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start
                    )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                }
                ColoredButton(
                    text = submitButtonText,
                    color = localColors.success,
                    onClick = onsubmit
                )
                ColoredButton(
                    text = dismissButtonText,
                    color = localColors.error,
                    onClick = onDismiss
                )
            }
        }
    }
}

val request = BookJob(
    type = "petentium",
    requestId = 7332,
    userName = "حسین امیری",
    rating = 3,
    address = "آبادانف کوی کارگرُ ردیف کیو ۲۷ اتاق ۳ُ طبقه اول واحد ۲ ۳",
    distance = 1163,
    serviceName = "Carole Gates",
    fare = "۲۳۰۰۰۰",
    fareType = "PerHour"

)

@Preview
@Composable
fun RequestDialogPreview() {
    RuzmozdProviderTheme(
        darkTheme = true
    ) {
        RequestDialog(
            title = "درخواست جدید",
            request = request,
            submitButtonText = stringResource(id = R.string.request_dialog_accept),
            dismissButtonText = stringResource(id = R.string.request_dialog_decline),
            onDismissRequest = {}, onsubmit = {}, onDismiss = {},
        )
    }
}