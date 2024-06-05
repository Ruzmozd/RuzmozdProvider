package ir.hirkancorp.presenter.screens.main

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.components.ErrorPage
import ir.hirkancorp.presenter.core.components.PermissionComponent
import ir.hirkancorp.presenter.core.components.dialogs.RuzmozdDialog
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
    navigateToLoginScreen: () -> Unit
) {

    val locationPermission = mutableListOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.run {
            onEvent(MainScreenEvent.CheckIfAuthenticate)
            authChannel.collectLatest {
                if (it.not()) navigateToLoginScreen()
            }
        }
    }

    PermissionComponent(
        requiredPermissions = locationPermission,
        onPermissionGranted = {
            viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermission(false))
            viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermissionError(false))
            viewModel.onEvent(MainScreenEvent.UpdateLocation)
        },
        onPermissionDenied = { permissions ->
            if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) && permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermission(true))
                viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermissionError(true))
            }
        },
        onPermissionsRevoked = { permissions ->
            if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) && permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermission(true))
                viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermissionError(true))
            }
        }
    )

    when {
        state.locationErrorDialog -> RuzmozdDialog(
            title = stringResource(id = R.string.app_name),
            content = stringResource(R.string.main_screen_location_error_dialog_content),
            submitButtonText = stringResource(id = R.string.all_submit),
            dismissOnClickOutside = false,
            onConfirmation = { viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermission(false)) }
        )
    }

    Scaffold(
        modifier = modifier
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                state.missedLocationPermission -> ErrorPage(
                    errorMessage = stringResource(
                        R.string.main_screen_missed_location_error_page_content
                    )
                )
            }
        }
    }
}