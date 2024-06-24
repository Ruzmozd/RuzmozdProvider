package ir.hirkancorp.presenter.screens.job_progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.components.ButtonWithProgressIndicator
import ir.hirkancorp.presenter.core.components.TextInput
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme

@Composable
fun RatingComponent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    rateError: String = "",
    providerName: String = stringResource(R.string.job_progress_screen_rating_provider_name),
    onRatingSubmitClick: (rate: Int, comment: String) -> Unit
) {

    var rate by remember { mutableIntStateOf(0) }
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

            Text(
                text = stringResource(R.string.job_progress_screen_rate_provider, providerName),
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (index in 1..5) {
                    val isSelected = index <= rate
                    val icon = if (isSelected) Icons.Outlined.Star else Icons.Outlined.Star
                    val iconTintColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = .1f)
                    Icon(
                        imageVector = icon,
                        tint = iconTintColor,
                        contentDescription = "Rating Star",
                        modifier = Modifier
                            .size(57.dp)
                            .selectable(
                                selected = isSelected,
                                onClick = { rate = index }
                            ),
                    )
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
            if (rateError.isNotBlank()) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = rateError, color = MaterialTheme.colors.error
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            ButtonWithProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.all_submit),
                isLoading = isLoading
            ) {
                onRatingSubmitClick(rate, comment)
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingComponentPreview() {
    RuzmozdProviderTheme(
        darkTheme = true
    ) {
        RatingComponent(
            onRatingSubmitClick = { rate: Int, comment: String ->  },
            rateError = "خطا"
        )
    }
}