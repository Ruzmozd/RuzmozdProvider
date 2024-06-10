package ir.hirkancorp.presenter.screens.main

import AnimatedSwitch
import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ir.hirkancorp.domain.provider_profile.models.ProviderStatusEnum
import ir.hirkancorp.domain.provider_profile.models.ProviderStatusEnum.INACTIVE
import ir.hirkancorp.domain.provider_profile.models.ProviderStatusEnum.PENDING
import ir.hirkancorp.domain.provider_profile.models.ProviderStatusEnum.REJECTED
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.components.PermissionComponent
import ir.hirkancorp.presenter.core.components.dialogs.RuzmozdDialog
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

    LaunchedEffect(key1 = state.profileState) {
        if (state.profileState is ProviderProfileState.Success) {
            when (state.profileState.providerProfile?.status) {
                PENDING, REJECTED, INACTIVE -> {
                    viewModel.onEvent(
                        MainScreenEvent.ShowProviderStatusDialog(
                            show = true,
                            message = state.profileState.providerProfile.statusDescription
                        )
                    )
                }

                else -> {}
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
            if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) && permissions.contains(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermission(true))
                viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermissionError(true))
            }
        },
        onPermissionsRevoked = { permissions ->
            if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) && permissions.contains(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
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
            onConfirmation = {
                viewModel.onEvent(
                    MainScreenEvent.HandleMissedLocationPermission(
                        false
                    )
                )
            }
        )

        state.providerStatusDialog -> RuzmozdDialog(
            title = stringResource(id = R.string.app_name),
            content = state.providerStatusDialogMessage,
            submitButtonText = stringResource(id = R.string.all_submit),
            dismissOnClickOutside = false,
            onConfirmation = {
                viewModel.onEvent(
                    MainScreenEvent.ShowProviderStatusDialog(
                        show = false,
                        message = ""
                    )
                )
            }
        )
    }


    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
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
                        controller.setZoom(8.0)
                        maxZoomLevel = 20.0
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
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(percent = 50), clip = true)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(MaterialTheme.colors.surface)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (state.profileState is ProviderProfileState.Success && state.profileState.providerProfile?.isOnline == true) stringResource(
                        R.string.main_screen_provider_state_text_online
                    )
                    else stringResource(R.string.main_screen_provider_state_text_offline)
                )
                AnimatedSwitch(
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp),
                    enabled = state.profileState is ProviderProfileState.Success && state.profileState.providerProfile?.status == ProviderStatusEnum.ACTIVE,
                    isLoading = state.providerStatus is ProviderStatus.Loading,
                    checked = state.profileState is ProviderProfileState.Success && state.profileState.providerProfile?.isOnline == true
                ) { viewModel.onEvent(MainScreenEvent.UpdateProviderStatus(it)) }
            }
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