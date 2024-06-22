package ir.hirkancorp.presenter.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.core.LoggerUtil
import ir.hirkancorp.domain.auth.use_cases.AuthUseCase
import ir.hirkancorp.domain.provider_location.use_cases.ProviderLocationUseCase
import ir.hirkancorp.domain.provider_profile.use_cases.ProviderProfileUseCase
import ir.hirkancorp.domain.provider_status.use_cases.ProviderStatusUseCase
import ir.hirkancorp.domain.request.model.BookJob
import ir.hirkancorp.domain.update_device.use_cases.UpdateDeviceUseCase
import ir.hirkancorp.domain.utils.ApiResult.Error
import ir.hirkancorp.domain.utils.ApiResult.Loading
import ir.hirkancorp.domain.utils.ApiResult.Success
import ir.hirkancorp.domain.work_radius.use_cases.UpdateWorkRadiusUseCase
import ir.hirkancorp.presenter.core.firebaseMessaging.NOTIFICATION_STATE_BOOK_JOB
import ir.hirkancorp.presenter.core.firebaseMessaging.NotificationSharedFlowWrapper
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE_BOOK_JOB
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE_CANCEL_JOB
import ir.hirkancorp.presenter.core.firebaseMessaging.utils.NotificationConstants.TYPE_CANCEL_REQUEST
import ir.hirkancorp.presenter.core.utils.UiEvent
import ir.hirkancorp.presenter.screens.main.MainScreenEvent.CheckIfAuthenticate
import ir.hirkancorp.presenter.screens.main.MainScreenEvent.HandleMissedLocationPermission
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

class MainViewModel(
    private val authUseCase: AuthUseCase,
    private val providerProfileUseCase: ProviderProfileUseCase,
    private val providerStatusUseCase: ProviderStatusUseCase,
    private val updateDeviceUseCase: UpdateDeviceUseCase,
    private val workRadiusUseCase: UpdateWorkRadiusUseCase,
    private val providerLocationUseCase: ProviderLocationUseCase
) : ViewModel(), KoinComponent {

    private val bookJobNotificationSharedFlowWrapper: NotificationSharedFlowWrapper<BookJob> =
        get(named(NOTIFICATION_STATE_BOOK_JOB))

    var state by mutableStateOf(MainScreenState())
        private set

    private var _authChannel = Channel<Boolean>()
    var authChannel = _authChannel.receiveAsFlow()

    private var _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    init {
        coolectNotificationSharedFlow()
    }


    fun onEvent(event: MainScreenEvent) = when(event) {
        is CheckIfAuthenticate -> isAuthenticate()
        is HandleMissedLocationPermission -> handleMissedLocationPermission(event.show)
        is MainScreenEvent.HandleMissedLocationPermissionError -> handleMissedLocationPermissionError(event.show)
        is MainScreenEvent.HandleMissedNotificationPermission-> handleMissedNotificationPermissionError(event.show)
        is MainScreenEvent.GetProviderProfile -> getProviderProfile()
        is MainScreenEvent.UpdateProviderStatus -> updateProviderStatus(event.isOnline)
        is MainScreenEvent.UpdateLocation -> updateLocation(event.location)
        is MainScreenEvent.ShowProviderStatusDialog -> showProviderStatusDialog(event.show, event.message)
        is MainScreenEvent.UpdateDevice -> updateDevice(event.deviceId)
        is MainScreenEvent.UpdateWorkRadius -> updateWorkRadius(radius = event.radius)
        is MainScreenEvent.HandleNotification -> handleNotification(type = event.type, id = event.id)
        is MainScreenEvent.ShowJobRequestDialog -> showJobRequestDialog(show = event.show, job = event.job)
    }

    private fun showJobRequestDialog(show: Boolean, job: BookJob?) {
        state = state.copy(showJobRequestDialog = show, job = job)
    }

    private fun handleNotification(type: String, id: Int) = when (type) {
        TYPE_BOOK_JOB -> {}
        TYPE_CANCEL_REQUEST -> {}
        TYPE_CANCEL_JOB -> {}
        else -> null
    }

    private fun coolectNotificationSharedFlow() {
        viewModelScope.launch {
            bookJobNotificationSharedFlowWrapper.message.collect { bookJoob ->
                state = state.copy(
                    requestNotificationState = NotificationEvent.JobRequest(bookJoob)
                )
            }
        }
    }

    private fun handleMissedNotificationPermissionError(show: Boolean) {
        state = state.copy(missedNotificationPermission = show)
    }

    private fun showProviderStatusDialog(show: Boolean, message: String) {
        state = state.copy(
            providerStatusDialogMessage = message,
            providerStatusDialog = show
        )
    }

    private fun updateProviderStatus(isOnline: Boolean) {
        viewModelScope.launch {
            providerStatusUseCase.invoke(isOnline).collect { result ->
                when(result) {
                    is Loading -> state = state.copy(providerStatus = ProviderStatus.Loading)
                    is Success -> {
                        state = state.copy(providerStatus = ProviderStatus.Success)
                        onEvent(MainScreenEvent.GetProviderProfile)
                    }
                    is Error -> state =  state.copy(providerStatus = ProviderStatus.Error(result.message.orEmpty()))
                }
            }
        }
    }

    private fun getProviderProfile() {
        viewModelScope.launch {
            providerProfileUseCase.invoke().collect { result ->
                state = state.copy(
                    profileState = when(result) {
                        is Error ->  ProviderProfileState.Error(result.message.orEmpty())
                        is Loading ->  ProviderProfileState.Loading
                        is Success -> { ProviderProfileState.Success(result.data) }
                    }
                )
            }
        }
    }

    private fun updateDevice(deviceId: String) {
        viewModelScope.launch {
            updateDeviceUseCase.invoke(deviceId).collect { result ->
                LoggerUtil.logE("", result.toString())
                when(result) {
                    is Success -> {
                        state = state.copy(updateDeviceLoading = false)
                        result.data?.jobId?.let { _uiEvent.send(UiEvent.Navigate("")) } // navigate to job progress
                    }
                    is Error -> {
                        state =  state.copy(updateDeviceLoading = false)
                        result.message?.let { _uiEvent.send(UiEvent.ShowSnackBar(it)) }
                    }
                    is Loading -> state = state.copy(updateDeviceLoading = true)
                }
            }
        }
    }

    private fun updateWorkRadius(radius: Int) {
        viewModelScope.launch {
            workRadiusUseCase.invoke(radius = radius).collect { result ->
                when (result) {
                    is Loading -> {}
                    is Error -> _uiEvent.send(UiEvent.ShowSnackBar(result.message.orEmpty()))
                    is Success -> _uiEvent.send(UiEvent.ShowSnackBar(result.data.orEmpty()))
                }
            }
        }
    }

    private fun updateLocation(location: Pair<Double, Double>) {
        viewModelScope.launch {
            providerLocationUseCase.invoke(lat = location.first, lng = location.second).collect { result ->
                when(result) {
                    is Error -> _uiEvent.send(UiEvent.ShowSnackBar(result.message.orEmpty()))
                    is Loading -> {}
                    is Success -> { onEvent(MainScreenEvent.GetProviderProfile) }
                }
            }
        }
    }

    private fun handleMissedLocationPermissionError(show: Boolean) {
        state = state.copy(missedLocationPermission = show)
    }

    private fun handleMissedLocationPermission(show: Boolean) {
        state = state.copy(locationErrorDialog = show)
    }

    private fun isAuthenticate() {
        viewModelScope.launch {
            state = state.copy(isLoadingAuth = true)
            authUseCase.invoke().collect { isAuthenticate ->
                state = state.copy(isAuthenticate = isAuthenticate,isLoadingAuth = false)
                _authChannel.send(isAuthenticate)
            }
        }
    }



}