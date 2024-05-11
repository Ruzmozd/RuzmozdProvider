package ir.hirkancorp.presenter.screens.profile_image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.ImageUtils.compressImage
import ir.hirkancorp.presenter.core.ImageUtils.readBytes
import ir.hirkancorp.presenter.core.LocalSpacing
import ir.hirkancorp.presenter.core.components.Button
import ir.hirkancorp.presenter.core.components.ChooseImageBottomSheet
import ir.hirkancorp.presenter.core.components.CircularImage
import ir.hirkancorp.presenter.core.utils.asString
import ir.hirkancorp.presenter.screens.profile_image.ProfileImageEvent.SendUploadImageEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileImageScreen(
    viewModel: ProfileImageViewModel = koinViewModel(),
    onNextClick: () -> Unit,
) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val coroutine = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(Hidden)

    ChooseImageBottomSheet(modalSheetState = bottomSheetState, onImageSelected = { selectedImage ->
        selectedImage?.run {
            val imageByteArray = context.compressImage(this).readBytes()
            viewModel.onEvent(SendUploadImageEvent(imageByteArray.toList()))
        }

    }) {
        Scaffold { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    when {
                        state.isLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(200.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }

                    CircularImage(
                        modifier = Modifier
                            .width(190.dp)
                            .height(190.dp),
                        imageUrl = state.imageUrl
                    )
                }


                when {
                    state.error.asString().isNotEmpty() -> {
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))
                        Text(
                            modifier = Modifier.width(200.dp),
                            text = state.error.asString(),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(spacing.spaceMedium))

                TextButton(
                    modifier = Modifier.width(200.dp),
                    onClick = {
                        coroutine.launch {
                            bottomSheetState.show()
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.register_screen_edit_photo),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(spacing.spaceSmall))

                Button(
                    enabled = state.success.asString().isNotBlank(),
                    modifier = Modifier.width(200.dp),
                    text = stringResource(R.string.profileImage_screen_continueButton),
                    onClick = onNextClick
                )
            }
        }
    }

}