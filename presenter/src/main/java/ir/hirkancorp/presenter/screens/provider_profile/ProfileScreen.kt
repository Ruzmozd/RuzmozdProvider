package ir.hirkancorp.presenter.screens.profile

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.ImageUtils.compressImage
import ir.hirkancorp.presenter.core.ImageUtils.readBytes
import ir.hirkancorp.presenter.core.LocalSpacing
import ir.hirkancorp.presenter.core.components.ButtonWithProgressIndicator
import ir.hirkancorp.presenter.core.components.ChooseImageBottomSheet
import ir.hirkancorp.presenter.core.components.CircularImage
import ir.hirkancorp.presenter.core.components.TextInput
import ir.hirkancorp.presenter.screens.register.components.RuzmozdTopAppBar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = koinViewModel(),
    navigateUp: () -> Unit,
) {

    val state = viewModel.state
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val coroutine = rememberCoroutineScope()
    val itemFlagId = if (state.nationality == "iranian") R.drawable.all_iran else R.drawable.all_afghanistan


    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ProfileScreenEvent.UpdateProfile)
    }

    BackHandler {
        handleBackPress(viewModel, navigateUp)
    }

    ChooseImageBottomSheet(modalSheetState =  bottomSheetState , onImageSelected = { choosedImageUri ->
        choosedImageUri?.run {
            val imageByteArray = context.compressImage(this).readBytes()
            viewModel.onEvent(ProfileScreenEvent.UpdateImage(imageByteArray.toList()))
        }
    } ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                RuzmozdTopAppBar(
                    title = stringResource(R.string.profile_screen_title),
                    navigationIcon = Icons.AutoMirrored.Default.ArrowBack,
                    onNavigationIconClick = { handleBackPress(viewModel, navigateUp) }
                )
            }
        ) { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.TopCenter
            ) {
                Card(
                    modifier = Modifier
                        .padding(top = 70.dp)
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    elevation = 0.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .padding(top = 86.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        when {
                            state.inEditMode -> {
                                TextButton(
                                    modifier = Modifier.fillMaxWidth(.5f),
                                    onClick = {
                                        coroutine.launch {
                                            bottomSheetState.show()
                                        }
                                    }
                                ) {
                                    Text(
                                        text = stringResource(R.string.profile_screen_edit_photo),
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Spacer(modifier = Modifier.height(spacing.spaceLarge))
                            }
                        }

                        TextInput(
                            placeholder = stringResource(R.string.register_screen_name),
                            currentValue = state.firstName,
                            enabled = state.inEditMode
                        ) { viewModel.onEvent(ProfileScreenEvent.UpdateFirstName(it)) }

                        if (state.firstNameError.isNotBlank()) {
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                            Text(text = state.firstNameError, color = MaterialTheme.colors.error)
                        }

                        Spacer(modifier = Modifier.height(spacing.spaceMedium))

                        TextInput(
                            placeholder = stringResource(R.string.register_screen_lastname),
                            currentValue = state.lastName,
                            enabled = state.inEditMode
                        ) { viewModel.onEvent(ProfileScreenEvent.UpdateLastName(it)) }

                        if (state.lastNameError.isNotBlank()) {
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                            Text(text = state.lastNameError, color = MaterialTheme.colors.error)
                        }

                        Spacer(modifier = Modifier.height(spacing.spaceMedium))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.large,
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colors.onSurface.copy(alpha = .5f)
                            ),
                            elevation = 0.dp,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = stringResource(R.string.profile_screen_text_nationality))
                                Icon(
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height(24.dp),
                                    painter = painterResource(id = itemFlagId),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(spacing.spaceMedium))

                        TextInput(
                            placeholder = stringResource(R.string.register_screen_lastname),
                            currentValue = state.mobileNumber,
                            enabled = false
                        ) {}

                        Spacer(modifier = Modifier.height(spacing.spaceMedium))

                        AnimatedVisibility(visible = state.inEditMode) {
                            ButtonWithProgressIndicator(
                                isLoading = state.isLoading,
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.all_submit),
                                onClick = {
                                    viewModel.onEvent(ProfileScreenEvent.UpdateProfile)
                                }
                            )
                        }

                        AnimatedVisibility(
                            visible = !state.inEditMode,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            TextButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { viewModel.onEvent(ProfileScreenEvent.SetEditMode(true)) }
                            ) {
                                Text(
                                    text = stringResource(R.string.profile_screen_edit_profile),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        if (state.profileUpdateMessage.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                            Text(
                                text = state.profileUpdateMessage,
                                color = MaterialTheme.colors.primary
                            )
                        }

                    }

                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularImage(
                        modifier = Modifier
                            .width(140.dp)
                            .height(140.dp),
                        imageUrl = if (state.imageUrl.isNotBlank()) state.imageUrl else null
                    )
                }
            }
        }
    }
}

private fun handleBackPress(
    viewModel: ProfileScreenViewModel,
    navigateUp: () -> Unit
) {
    when {
        viewModel.state.inEditMode -> {
            viewModel.onEvent(ProfileScreenEvent.SetEditMode(false))
        }

        else -> navigateUp()
    }
}