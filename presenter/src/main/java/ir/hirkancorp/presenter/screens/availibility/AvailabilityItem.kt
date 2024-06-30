package ir.hirkancorp.presenter.screens.availibility

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme

@Composable
fun AvailabilityItem(
    modifier: Modifier = Modifier,
    time: Time,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = true, onCheckedChange = {})
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = time.time,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "(${time.value})",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

data class Time(
    val id: Int = 1378,
    val time: String = "09:00:00",
    val value: String = "09:00 - 10:00",
    val day: String = "monday",
    val status: Boolean = true,
)

@Preview
@Composable
private fun AvailabilityItemPreview() {
    RuzmozdProviderTheme {
        AvailabilityItem(time = Time())
    }
}