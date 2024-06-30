package ir.hirkancorp.presenter.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.components.dialogs.RuzmozdDialog
import ir.hirkancorp.presenter.core.utils.restart
import ir.hirkancorp.presenter.nav_graphs.settings.SettingsScreens
import ir.hirkancorp.presenter.screens.register.components.RuzmozdTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    navigateTo: (route: String) -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = state.isLoggedOut) {
        if (state.isLoggedOut) {
            context.restart()
        }
    }

    when {
        state.showDialog -> RuzmozdDialog(
            title = stringResource(R.string.settings_screen_logout_dialog_title),
            content = stringResource(R.string.settings_screen_logout_dialog_content),
            submitButtonText = stringResource(R.string.all_dialog_yes),
            dismissButtonText = stringResource(R.string.all_dialog_no),
            dismissOnClickOutside = true,
            onDismiss = { viewModel.onEvent(SettingsScreenEvent.HideDialog) },
            onConfirmation = { viewModel.onEvent(SettingsScreenEvent.LogOut) }
        )
    }

    Scaffold(
        topBar = {
            RuzmozdTopAppBar(title = stringResource(R.string.settings_screen_app_bar_title))
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray),
                ) {
                    SettingItem(
                        title = stringResource(R.string.settings_screen_item_profile),
                        icon = Icons.Outlined.Person,
                        onItemClick = {
//                            navigateTo(SettingsScreens.ProfileScreen.route)
                        }
                    )
                    SettingItem(
                        title = stringResource(R.string.settings_screen_item_referral),
                        icon = ImageVector.vectorResource(id = R.drawable.settings_referral),
                        onItemClick = {}
                    )
                    SettingItem(
                        title = stringResource(R.string.settings_screen_item_support),
                        icon =  ImageVector.vectorResource(id = R.drawable.settings_support),
                        onItemClick = {}
                    )
                    SettingItem(
                        title = stringResource(R.string.settings_screen_item_exit),
                        icon =  ImageVector.vectorResource(id = R.drawable.settings_exit),
                        contentColor = MaterialTheme.colors.error,
                        onItemClick = { viewModel.onEvent(SettingsScreenEvent.ShowDialog) }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector = Icons.Rounded.Settings,
    contentColor: Color = MaterialTheme.colors.onBackground,
    onItemClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = "itemIcon")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, style = MaterialTheme.typography.body1)
        }
    }
}