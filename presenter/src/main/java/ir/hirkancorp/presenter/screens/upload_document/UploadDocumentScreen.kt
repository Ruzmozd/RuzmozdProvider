package ir.hirkancorp.presenter.screens.upload_document

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.ImageUtils.compressImage
import ir.hirkancorp.presenter.core.ImageUtils.readBytes
import ir.hirkancorp.presenter.core.LocalSpacing
import ir.hirkancorp.presenter.core.components.ChooseImageBottomSheet
import ir.hirkancorp.presenter.core.utils.asString
import ir.hirkancorp.presenter.screens.upload_document.UploadDocumentEvent.SetImageUri
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UploadDocumentScreen(
    viewModel: UploadDocumentViewModel = koinViewModel(),
    onNextClick: () -> Unit,
) {

    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val coroutine = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(Hidden)

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(UploadDocumentEvent.InitScreen)
    }

    LaunchedEffect(key1 = state.success.asString(context)) {
        if (state.success.asString(context).isNotEmpty()) {
            onNextClick()
        }
    }

    ChooseImageBottomSheet(modalSheetState = bottomSheetState, onImageSelected = { selectedImage ->
        selectedImage?.run {
            val imageByteArray = context.compressImage(this).readBytes()
            viewModel.onEvent(SetImageUri(this, imageByteArray.toList()))
        }
    }) {
        Scaffold { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = stringResource(R.string.upload_document_screen_documents),
                    )
                    Text(
                        text = stringResource(R.string.upload_document_cancel),
                        modifier = Modifier.clickable(onClick = onNextClick)
                    )
                }

                Spacer(modifier = Modifier.height(spacing.spaceMedium))

                Text(text = stringResource(R.string.upload_document_screen_national_residence_image))
                Spacer(modifier = Modifier.height(spacing.spaceMedium))

                AsyncImage(
                    modifier = Modifier
                        .clickable {
                            coroutine.launch {
                                bottomSheetState.show()
                            }
                        }
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colors.primary)
                        .height(190.dp),
                    model = state.capturedImage,
                    contentScale = if (state.capturedImage == null) ContentScale.Fit else ContentScale.FillWidth,
                    contentDescription = null,
                    error = painterResource(R.drawable.plus)
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.upload_document_screen_nationality_code))
                    Spacer(modifier = Modifier.width(spacing.spaceMedium))
                    Text(text = viewModel.state.nationalCode.takeIf { it.isNotEmpty() }
                        ?: "1234567890")
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


                Spacer(modifier = Modifier.weight(1.0f))

                androidx.compose.material.Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    elevation = ButtonDefaults.elevation(0.dp),
                    shape = MaterialTheme.shapes.large,
                    enabled = state.capturedImage != null && state.isLoading.not(),
                    onClick = { viewModel.onEvent(UploadDocumentEvent.SendDocumentEvent) }
                ) {
                    if (state.isLoading)
                        CircularProgressIndicator()
                    else Text(
                        text = stringResource(R.string.upload_document_continue),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }

}