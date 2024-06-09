package ir.hirkancorp.presenter.screens.main.components

import AnimatedSwitch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.domain.provider_profile.models.Location
import ir.hirkancorp.domain.provider_profile.models.ProviderProfile
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.Dimensions
import ir.hirkancorp.presenter.core.LocalSpacing
import ir.hirkancorp.presenter.core.components.CircularImage
import ir.hirkancorp.presenter.core.theme.AlertColors
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme
import ir.hirkancorp.presenter.core.theme.localAlertColors
import ir.hirkancorp.presenter.screens.main.ProviderProfileState
import ir.hirkancorp.presenter.screens.main.ProviderProfileState.Error
import ir.hirkancorp.presenter.screens.main.ProviderProfileState.Loading
import ir.hirkancorp.presenter.screens.main.ProviderProfileState.Success

@Composable
fun ProviderInfoCard(modifier: Modifier = Modifier, profileState: ProviderProfileState) {

    val spacing = LocalSpacing.current
    val alertsColors = localAlertColors.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 90.dp),
        shape = RoundedCornerShape(topStart = spacing.spaceLarge, topEnd = spacing.spaceLarge)
    ) {
        when (profileState) {
            is Error -> ProviderInfoError(errorMessage = "An Error")
            is Success -> ProviderInfo(profile = profileState.providerProfile!!, spacing, alertsColors)
            is Loading -> ProviderInfoLoading(spacing = spacing)
        }

    }
}

@Composable
fun ProviderInfoError(
    modifier: Modifier = Modifier,
    errorMessage: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.error),
            imageVector = ImageVector.vectorResource(R.drawable.all_warning),
            contentDescription = "error"
        )
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProviderInfo(
    profile: ProviderProfile,
    spacing: Dimensions,
    alertsColors: AlertColors
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircularImage(
                modifier = Modifier.size(60.dp),
                imageUrl = profile.profileImage
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Column {
                Text(
                    text = profile.firstName + " " + profile.lastName,
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
                Row {
                    Card(
                        modifier = Modifier,
                        backgroundColor = alertsColors.success.copy(alpha = .3f),
                        contentColor = MaterialTheme.colors.onSurface.copy(alpha = .7f),
                        elevation = 0.dp
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(
                                R.string.main_screen_provider_info_completed_jobs,
                                profile.completedJobsCount
                            ),
                            style = MaterialTheme.typography.caption
                        )
                    }
                    Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
                    Card(
                        modifier = Modifier,
                        backgroundColor = alertsColors.error.copy(alpha = .3f),
                        contentColor = MaterialTheme.colors.onSurface.copy(alpha = .7f),
                        elevation = 0.dp
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(
                                R.string.main_screen_provider_info_pending_jobs,
                                profile.pendingJobsCount
                            ),
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProviderInfoLoading(spacing: Dimensions) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularImage(
                modifier = Modifier.size(60.dp),
                imageUrl = null
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Column {
                Row(
                    modifier = Modifier
                        .width(130.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {}
                Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
                Row {
                    Row(
                        modifier = Modifier
                            .width(80.dp)
                            .clip(shape = MaterialTheme.shapes.medium)
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {}
                    Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
                    Row(
                        modifier = Modifier
                            .width(80.dp)
                            .clip(shape = MaterialTheme.shapes.medium)
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {}
                }
            }
        }
    }
}

val profile = ProviderProfile(
    checkedProviderAvailability = false,
    checkedProviderServiceType = false,
    city = "Gay Meadows",
    completedJobsCount = "۲۳",
    firstName = "حسین",
    id = 8567,
    isOnline = false,
    lastName = "سعدون",
    location = Location(
        latitude = "meliore",
        longitude = "error",
        workRadius = 7526
    ),
    mobileNumber = "tacimates",
    nationalityCode = "etiam",
    pendingJobsCount = "۳",
    profileImage = "efficitur",
    serviceDescription = "سرویس دهنده و اطلاعات گیرنده",
    status = "placerat"
)

@Preview
@Composable
private fun ProviderInfoPreview() {
    RuzmozdProviderTheme {
        Column {
            ProviderInfoCard(profileState = Error("خطای ناشناخته"))
            ProviderInfoCard(profileState = Loading)
            ProviderInfoCard(profileState = Success(profile))
        }
    }
}