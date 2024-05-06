package ir.hirkancorp.presenter.core.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.LocalSpacing
import ir.hirkancorp.presenter.core.components.Button
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme


@Composable
fun RuzmozdDialog(
    title: String,
    content: String? = null,
    icon: ImageVector = Icons.Default.Info,
    submitButtonText: String = stringResource(id = R.string.all_submit),
    dismissButtonText: String? = null,
    dismissOnClickOutside: Boolean = false,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnClickOutside = dismissOnClickOutside
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
                content?.let {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier.weight(0.5f),
                        text = submitButtonText,
                        onClick = onConfirmation
                    )
                    if (dismissButtonText.isNullOrBlank().not()) {
                        Spacer(modifier = Modifier.width(spacing.spaceMedium))
                        Button(
                            modifier = Modifier.weight(0.5f),
                            text = dismissButtonText!!,
                            onClick = onDismiss
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun RuzmozdDialogPreview() {
    RuzmozdProviderTheme {
        RuzmozdDialog(
            title = "روزمزد",
            content = "این یک متن آزمایشی است",
            onDismissRequest = {},
        )
    }
}