package ir.hirkancorp.presenter.screens.login

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.core.DateUtils.toTime
import ir.hirkancorp.core.LoggerUtil
import ir.hirkancorp.domain.login.model.User
import ir.hirkancorp.domain.login.use_cases.LoginUseCase
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private lateinit var countDownTimer: CountDownTimer
    private val timerMillisInFuture = 120000L
    private val intervalMillis = 1000L

    var loginState by mutableStateOf(LoginState())
        private set

    init {
        timer()
    }


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.AuthenticateOtp -> authenticateOtp(event.otp)
            is LoginEvent.SendOtpRequest -> sendOtpRequest(event.phoneNumber)
        }
    }


    private fun sendOtpRequest(phoneNumber: String) = viewModelScope.launch {
        viewModelScope.launch {
            loginUseCase.invoke(phoneNumber).collect { result ->
                loginState = when (result) {
                    is ApiResult.Loading -> {
                        loginState.copy(
                            isLoading = true
                        )
                    }

                    is ApiResult.Success -> {
                        startTimer()
                        loginState.copy(
                            isLoading = false,
                            phoneNumber = phoneNumber,
                            otpSent = true,
                            success = result.data
                        )
                    }

                    is ApiResult.Error -> {
                        loginState.copy(
                            isLoading = false,
                            success = null,
                            error = result.message
                        )
                    }

                }
            }
        }
    }

    private fun authenticateOtp(otp: String) = viewModelScope.launch {
        stopTimer()

        val user = loginState.phoneNumber?.let { phoneNumber ->
            User(
                phoneNumber = phoneNumber,
                otp = otp
            )
        }
        viewModelScope.launch {
            user?.let {
                loginUseCase.invoke(user).collect { result ->
                    loginState = when (result) {
                        is ApiResult.Loading -> {
                            loginState.copy(
                                isLoading = true,
                                success = "Please wait"
                            )
                        }

                        is ApiResult.Success -> {
                            loginState.copy(
                                isLoading = false,
                                success = "Success login",
                                userId = result.data?.userId,
                                navigate = true
                            )
                        }

                        is ApiResult.Error -> {
                            loginState.copy(
                                isLoading = false,
                                success = null,
                                error = result.message
                            )
                        }

                    }
                }

            }

        }
    }


    private fun startTimer() = countDownTimer.start()

    private fun stopTimer() = countDownTimer.cancel()

    private fun timer() {
        countDownTimer = object : CountDownTimer(timerMillisInFuture, intervalMillis) {
            override fun onTick(millisUntilFinished: Long) {
                LoggerUtil.logE(::timer.name, millisUntilFinished.toTime())
                loginState = loginState.copy(
                    resendOtpTimer = millisUntilFinished.toTime()
                )
            }

            override fun onFinish() {
                loginState = loginState.copy(
                    resendOtpTimer = null
                )
            }
        }
    }

}