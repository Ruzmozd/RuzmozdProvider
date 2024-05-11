package ir.hirkancorp.presenter.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme


@Composable
fun ButtonWithProgressIndicator(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = MaterialTheme.shapes.large,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        enabled = !isLoading,
        onClick = onClick,
        contentPadding = PaddingValues(12.dp)
    ) {
        if (isLoading) CircularProgressIndicator()
        else Text(
            text = text, style = MaterialTheme.typography.button
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun ButtonWithProgressIndicatorPreview() {
    RuzmozdProviderTheme {
        Box(
            modifier = Modifier.padding(32.dp)
        ) {
            ButtonWithProgressIndicator(
                text = "Test",
                isLoading = false,
                onClick = {}
            )
        }
    }
}