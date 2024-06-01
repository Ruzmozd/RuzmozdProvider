package ir.hirkancorp.presenter.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.navigation.BottomNavigationScreens
import ir.hirkancorp.presenter.nav_graphs.settings.SettingsScreens

@Composable
fun RuzmozdBottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = listOf(
        BottomNavigationScreens.MainScreen(
            screenTitle = stringResource(id = R.string.app_name),
            screenIcon = Icons.Outlined.Home
        ),
        BottomNavigationScreens.RequestScreen(
            screenTitle = stringResource(id = R.string.requests_screen_app_bar_title),
            screenIcon = Icons.AutoMirrored.Outlined.List
        ),
        BottomNavigationScreens.SettingsScreen(
            screenTitle = stringResource(id = R.string.settings_screen_app_bar_title),
            screenIcon = Icons.Outlined.Settings
        ),

    )
    val bottomBarDestination = screens.any { it.route == currentDestination?.route } || currentDestination?.route == SettingsScreens.SettingsScreen.route
    if (bottomBarDestination) {
        BottomNavigation(
            modifier = modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface,
            elevation = 0.dp,
        ) {
            screens.forEach { screen ->
                NavItem(
                    screen = screen,
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    }
}

@Composable
fun RowScope.NavItem(
    screen: BottomNavigationScreens,
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    val selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true
    val selectedIconBackground = if (selected) MaterialTheme.colors.primary.copy(alpha = .2f) else Color.Unspecified
    BottomNavigationItem(
        selected = selected,
        onClick = {
            navController.navigate(screen.route)
        },
        icon = {
            Icon(
                modifier = Modifier
                    .width(48.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(selectedIconBackground),
                imageVector = screen.icon,
                contentDescription = "NavIcon"
            )
        },
        label = {
            Text(text = screen.title, fontWeight = FontWeight.Bold)
        },
        alwaysShowLabel = true,
        selectedContentColor = MaterialTheme.colors.primary,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
    )
}