package ir.hirkancorp.presenter.screens.register

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.LocalSpacing
import ir.hirkancorp.presenter.core.components.ButtonWithProgressIndicator
import ir.hirkancorp.presenter.core.components.TextInput
import ir.hirkancorp.presenter.core.utils.asString
import ir.hirkancorp.presenter.screens.register.RegisterEvent.*
import ir.hirkancorp.presenter.screens.register.components.RuzmozdTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    phoneNumber: String?,
    viewModel: RegisterViewModel = koinViewModel(),
    navigateUp: () -> Unit,
    navigateToProfileImageScreen: (token: String) -> Unit,
) {


    var expended by remember { mutableStateOf(false) }
    val spacing = LocalSpacing.current
    val state = viewModel.state

    when {
        state.token.isNotBlank() -> {
            navigateToProfileImageScreen(state.token)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            RuzmozdTopAppBar(
                title = stringResource(R.string.all_register),
                navigationIcon = Icons.AutoMirrored.Default.ArrowBack,
                onNavigationIconClick = navigateUp
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextInput(
                placeholder = stringResource(R.string.register_screen_name),
                currentValue = state.name
            ) { viewModel.onEvent(UpdateName(it)) }

            if (state.nameError.asString().isNotBlank()) {
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Text(text = state.nameError.asString(), color = MaterialTheme.colors.error)
            }

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            TextInput(
                placeholder = stringResource(R.string.register_screen_lastname),
                currentValue = state.lastName
            ) { viewModel.onEvent(UpdateLastName(it)) }

            if (state.lastNameError.asString().isNotBlank()) {
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Text(text = state.lastNameError.asString(), color = MaterialTheme.colors.error)
            }

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expended = !expended }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (state.city.first > 0) state.city.second else stringResource(id = R.string.register_screen_city),
                        style = MaterialTheme.typography.body1
                    )
                    Icon(
                        modifier = Modifier.rotate(if (expended) 180f else 0f),
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "dropDown"
                    )
                }
                DropdownMenu(
                    expanded = expended,
                    onDismissRequest = { expended = false },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    state.cities.map {
                        DropdownMenuItem(
                            onClick = {
                                viewModel.onEvent(UpdateCity(it.id to it.name))
                                if (expended) expended = false
                            }
                        ) {
                            Text(text = it.name, style = MaterialTheme.typography.body1)
                        }
                    }

                }

            }

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            TextInput(
                placeholder = stringResource(R.string.register_screen_nationality_code),
                currentValue = state.nationalCode,
                onValueChange = { viewModel.onEvent(UpdateNationalCode(it)) },
                keyboardType = KeyboardType.Number
            )

            if (state.nationalCodeError.asString().isNotEmpty()) {
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Text(text = state.nationalCodeError.asString(), color = MaterialTheme.colors.error)
            }

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            TextInput(
                placeholder = stringResource(R.string.register_screen_referral_code),
                currentValue = state.referralCode,
                onValueChange = { viewModel.onEvent(UpdateReferralCode(it)) },
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            ButtonWithProgressIndicator(
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.register_screen_submit_and_register),
                onClick = {
                    phoneNumber?.let { viewModel.onEvent(SendRegisterEvent(it)) }
                }
            )

            if (state.registrationMessage.asString().isNotEmpty()) {
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Text(
                    text = state.registrationMessage.asString(),
                    color = MaterialTheme.colors.primary
                )
            }

        }
    }
}