package ir.hirkancorp.presenter.screens.main

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.components.ErrorPage
import ir.hirkancorp.presenter.core.components.PermissionComponent
import ir.hirkancorp.presenter.core.components.dialogs.RuzmozdDialog
import ir.hirkancorp.presenter.core.utils.UiEvent
import ir.hirkancorp.presenter.screens.main.components.ProviderInfoCard
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView

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
                else onEvent(MainScreenEvent.GetProviderProfile)
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
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->
                    val mapCenterPoint = GeoPoint(35.69944, 51.33778)
                    val map = MapView(context).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
                        controller.setCenter(mapCenterPoint)
                        controller.setZoom(16.0)
                        maxZoomLevel = 22.0
                        addMapListener(object : MapListener {
                            override fun onScroll(event: ScrollEvent?): Boolean {
                                event?.source?.mapCenter?.let {
                                    val lat = it.latitude
                                    val lng = it.longitude
                                }
                                return false
                            }

                            override fun onZoom(event: ZoomEvent?): Boolean {
                                return true
                            }

                        })
                    }
                    Configuration.getInstance().userAgentValue =
                        System.currentTimeMillis().toString()
                    map
                }
            )
            ProviderInfoCard(
                modifier = Modifier.align(Alignment.BottomCenter),
                profileState = state.profileState
            )
//            when {
//                state.missedLocationPermission -> ErrorPage(
//                    errorMessage = stringResource(
//                        R.string.main_screen_missed_location_error_page_content
//                    )
//                )
//            }
        }
    }
}