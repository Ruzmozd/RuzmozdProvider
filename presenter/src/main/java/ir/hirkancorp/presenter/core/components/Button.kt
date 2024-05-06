package ir.hirkancorp.presenter.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.core.LocalSpacing
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme


@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    iconVector: ImageVector? = null,
    onClick: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        enabled = enabled,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        onClick = onClick,
        contentPadding = PaddingValues(12.dp)
    ) {
        if (iconVector != null) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
            Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
        }
        Text(text = text, style = MaterialTheme.typography.button)
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun Buttona() {
    val spacing = LocalSpacing.current
    RuzmozdProviderTheme {
        Button(
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            onClick = { },
            contentPadding = PaddingValues(12.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
            Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
            Text(text = "کلیک", style = MaterialTheme.typography.button)
        }
    }
}