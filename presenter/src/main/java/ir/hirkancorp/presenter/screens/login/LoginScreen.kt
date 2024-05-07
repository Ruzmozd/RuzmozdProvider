package ir.hirkancorp.presenter.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.hirkancorp.core.LoggerUtil
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.LocalSpacing
import ir.hirkancorp.presenter.screens.login.components.LoginGuideText
import ir.hirkancorp.presenter.screens.login.components.LoginImage
import ir.hirkancorp.presenter.screens.login.components.NumberInputSection
import ir.hirkancorp.presenter.screens.login.components.OtpSection
import org.koin.androidx.compose.koinViewModel
import ir.hirkancorp.presenter.screens.login.LoginEvent.AuthenticateOtp
import ir.hirkancorp.presenter.screens.login.LoginEvent.SendOtpRequest
import androidx.compose.runtime.LaunchedEffect

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    navigateToRegisterScreen: (String?) -> Unit,
    navigateToMainScreen: () -> Unit
) {
    val state = viewModel.loginState
    val spacing = LocalSpacing.current
    LaunchedEffect(
        key1 = state.success.isNullOrBlank(),
        key2 = state.navigate,
        block = {
            if (!state.success.isNullOrBlank() && state.navigate) {
                LoggerUtil.logI("NAVIGATE", "Navigate")
                // navigate
                state.userId?.let { id ->
                    if (id == 0) {
                        navigateToRegisterScreen(state.phoneNumber)
                    } else {
                        navigateToMainScreen()
                    }
                }
            }
        })

    Scaffold { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LoginImage(resId = R.drawable.handy_regiater_background)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (!state.success.isNullOrBlank() && !state.resendOtpTimer.isNullOrBlank()) {
                    LoginGuideText(text = stringResource(R.string.auth_screen_enter_otp))
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LoginGuideText(text = stringResource(R.string.auth_screen_otp_not_received))
                        Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
                        LoginGuideText(
                            text = if (state.resendOtpTimer == "00:00") stringResource(id = R.string.auth_screen_resend_otp)
                            else stringResource(
                                R.string.auth_screen_timer_otp,
                                state.resendOtpTimer
                            ),
                            color = MaterialTheme.colors.primary,
                            onClick = {
                                if (state.resendOtpTimer == "00:00") {
                                    state.phoneNumber?.let { viewModel.onEvent(SendOtpRequest(it)) }
                                }
                            }
                        )
                    }

                } else if (!state.error.isNullOrBlank()) {
                    LoginGuideText(
                        text = state.error,
                        color = MaterialTheme.colors.error
                    )
                } else LoginGuideText(
                    text = stringResource(R.string.auth_screen_enter_phone_number),
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))

                if (state.otpSent) {
                    OtpSection(
                        placeholder = stringResource(id = R.string.auth_screen_otp_placeholder),
                        textFieldMaxChar = 4,
                        isLoading = state.isLoading,
                        onSubmitClick = {
                            viewModel.onEvent(AuthenticateOtp(it))
                        }
                    )
                } else NumberInputSection(
                    textFieldMaxChar = 11,
                    placeholder = stringResource(R.string.auth_screen_phone_number_placeholder),
                    isLoading = state.isLoading,
                    onSubmitClick = {
                        viewModel.onEvent(SendOtpRequest(it))
                    })
            }
        }
    }
}