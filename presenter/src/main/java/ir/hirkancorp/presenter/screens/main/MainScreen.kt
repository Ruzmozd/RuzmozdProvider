package ir.hirkancorp.presenter.screens.main

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ir.hirkancorp.domain.provider_profile.models.ProviderStatusEnum.INACTIVE
import ir.hirkancorp.domain.provider_profile.models.ProviderStatusEnum.PENDING
import ir.hirkancorp.domain.provider_profile.models.ProviderStatusEnum.REJECTED
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.components.PermissionComponent
import ir.hirkancorp.presenter.core.components.dialogs.RequestDialog
import ir.hirkancorp.presenter.core.components.dialogs.RuzmozdDialog
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.FirebaseUtils
import ir.hirkancorp.presenter.core.utils.LocationUtil
import ir.hirkancorp.presenter.core.utils.UiEvent
import ir.hirkancorp.presenter.core.utils.UiText
import ir.hirkancorp.presenter.core.utils.asString
import ir.hirkancorp.presenter.core.utils.findActivity
import ir.hirkancorp.presenter.screens.main.components.ProviderInfoCard
import ir.hirkancorp.presenter.screens.main.components.ProviderStateComponent
import ir.hirkancorp.presenter.screens.main.components.ProviderStateComponentLoading
import ir.hirkancorp.presenter.screens.main.components.WorkRadius
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
    navigateToLoginScreen: () -> Unit,
    navigateToJobScreen: (jobId: Int) -> Unit,
) {

    val locationPermission = mutableListOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        locationPermission.apply { add(Manifest.permission.POST_NOTIFICATIONS) }
    } else {
        locationPermission
    }

    val context = LocalContext.current
    val state = viewModel.state
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.run {
            onEvent(MainScreenEvent.CheckIfAuthenticate)
            authChannel.collectLatest {
                if (it.not()) navigateToLoginScreen()
                else {
                    FirebaseUtils.getFirebaseToken { deviceId ->
                        onEvent(MainScreenEvent.UpdateDevice(deviceId))
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when(uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(uiEvent.message)
                }

                is UiEvent.Navigate -> {}
                UiEvent.NavigateUp -> {}
            }
        }
    }

    when {
        state.isLoadingAuth -> {}
        state.isAuthenticate -> {

            PermissionComponent(
                requiredPermissions = requiredPermissions,
                onPermissionGranted = {
                    viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermission(false))
                    updateLocation(context, viewModel, scope, snackbarHostState)
                },
                onPermissionDenied = { permissions ->
                    if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) && permissions.contains(
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    ) {
                        viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermission(true))
                    }
                    if (permissions.contains(Manifest.permission.POST_NOTIFICATIONS)) {
                        viewModel.onEvent(MainScreenEvent.HandleMissedNotificationPermission(true))
                    }
                },
                onPermissionsRevoked = { permissions ->
                    if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) && permissions.contains(
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    ) {
                        viewModel.onEvent(MainScreenEvent.HandleMissedLocationPermission(true))
                    }
                    if (permissions.contains(Manifest.permission.POST_NOTIFICATIONS)) {
                        viewModel.onEvent(MainScreenEvent.HandleMissedNotificationPermission(true))
                    }
                }
            )

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

            LaunchedEffect(key1 = true) {
                context.findActivity()?.intent?.extras?.let { bundle ->
                    viewModel.onEvent(MainScreenEvent.HandleNotification(bundle))
                }
            }

            when(state.requestNotificationState) {
                is NotificationEvent.Idle -> viewModel.onEvent(MainScreenEvent.ShowJobRequestDialog(show = false, job = null))
                is NotificationEvent.CancelRequest -> viewModel.onEvent(MainScreenEvent.ShowJobRequestDialog(show = false, job = null))
                is NotificationEvent.JobRequest -> viewModel.onEvent(MainScreenEvent.ShowJobRequestDialog(show = true, job = state.requestNotificationState.job))
            }

            when {
                state.locationErrorDialog -> RuzmozdDialog(
                    title = stringResource(id = R.string.app_name),
                    content = stringResource(R.string.main_screen_missed_location_error),
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

                state.missedNotificationPermission -> RuzmozdDialog(
                    title = stringResource(id = R.string.app_name),
                    content = stringResource(R.string.main_screen_missed_notification_permission_error),
                    submitButtonText = stringResource(id = R.string.all_submit),
                    dismissOnClickOutside = false,
                    onConfirmation = {
                        viewModel.onEvent(
                            MainScreenEvent.HandleMissedNotificationPermission(
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

                state.showJobRequestDialog -> {
                    state.job?.let { job ->
                        RequestDialog(
                            title = stringResource(R.string.main_screen_request_dialog_title_new_request),
                            request = job,
                            timerState = state.timerState,
                            acceptRequestLoading = state.acceptRequestLoading,
                            cancelRequestLoading = state.cancelRequestLoading,
                            onSubmit = { requestId ->
                                viewModel.onEvent(MainScreenEvent.AcceptRequest(requestId))
                            },
                            onDismiss = { requestId ->
                                viewModel.onEvent(MainScreenEvent.CancelRequest(requestId))
                            }
                        )
                    }
                }

            }

            Scaffold(
                modifier = modifier.fillMaxSize(),
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
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
                    Column(modifier = Modifier.align(Alignment.TopCenter)) {
                        if (state.updateDeviceLoading)
                            ProviderStateComponentLoading()
                        else ProviderStateComponent(
                            state = state,
                            onCheckedChange = { checked ->
                                viewModel.onEvent(MainScreenEvent.UpdateProviderStatus(checked))
                            }
                        )
                        if (state.profileState is ProviderProfileState.Success) {
                            WorkRadius(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                radius = state.profileState.providerProfile?.location?.workRadius!!,
                                setRadius = { viewModel.onEvent(MainScreenEvent.UpdateWorkRadius(it)) }
                            )
                        }
                    }
                    ProviderInfoCard(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        profileState = state.profileState
                    )
                }
            }
        }
    }


}

private fun updateLocation(
    context: Context,
    viewModel: MainViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    LocationUtil.getCurrentLocation(
        context = context,
        onGetCurrentLocationSuccess = { location ->
            viewModel.onEvent(MainScreenEvent.UpdateLocation(location))
        },
        onGetCurrentLocationFailed = {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = UiText.StringResource(R.string.main_screen_location_error_snackbar)
                        .asString(context)
                )
            }
        }
    )
}