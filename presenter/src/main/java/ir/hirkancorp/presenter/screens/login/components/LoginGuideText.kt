package ir.hirkancorp.presenter.screens.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LoginGuideText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier
            .wrapContentWidth()
            .clickable { onClick() },
        text = text,
        style = MaterialTheme.typography.body1,
        color = color,
        textAlign = TextAlign.Center,
    )
}