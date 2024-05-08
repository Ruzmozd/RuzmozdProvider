package ir.hirkancorp.presenter.screens.register.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme


@Composable
fun RuzmozdTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = null,
    onNavigationIconClick: () -> Unit = {},
    actions: (@Composable RowScope.() -> Unit)? = {}
) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.h6) },
        modifier = modifier.fillMaxWidth(),
        navigationIcon = navigationIcon?.let {
            {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = it,
                        contentDescription = null
                    )
                }
            }
        },
        actions = actions ?: {},
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 0.5.dp
    )
}

@Preview
@Composable
fun AppBar() {
    RuzmozdProviderTheme(
        darkTheme = true
    ) {
        TopAppBar(
            title = { Text(text = "روزمزد", style = MaterialTheme.typography.h6) },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 1.dp
        )
    }
}