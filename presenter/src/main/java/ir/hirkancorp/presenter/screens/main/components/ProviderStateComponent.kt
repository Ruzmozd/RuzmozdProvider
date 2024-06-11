package ir.hirkancorp.presenter.screens.main.components

import AnimatedSwitch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.domain.provider_profile.models.ProviderStatusEnum
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme
import ir.hirkancorp.presenter.screens.main.MainScreenState
import ir.hirkancorp.presenter.screens.main.ProviderProfileState
import ir.hirkancorp.presenter.screens.main.ProviderStatus


@Composable
fun ProviderStateComponent(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    onCheckedChange: (checked: Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(percent = 50),
                clip = true
            )
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
        ) { checked -> onCheckedChange(checked) }
    }
}

@Composable
fun ProviderStateComponentLoading(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(percent = 50),
                clip = true
            )
            .clip(RoundedCornerShape(percent = 50))
            .background(MaterialTheme.colors.surface)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .width(120.dp)
                .height(16.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(MaterialTheme.colors.onSurface.copy(alpha = .3f)),
        ) {}
        Row(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(MaterialTheme.colors.onSurface.copy(alpha = .3f)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(MaterialTheme.colors.surface),
            ) {}
        }
    }
}

@Preview
@Composable
private fun ProviderStatePreview() {
    RuzmozdProviderTheme {
        Column {
            ProviderStateComponentLoading()
        }
    }
}