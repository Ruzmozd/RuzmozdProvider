package ir.hirkancorp.presenter.core.components

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.PermissionStatus.Denied
import com.google.accompanist.permissions.PermissionStatus.Granted
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.ImageUtils.createImageFile
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChooseImageBottomSheet(
    modalSheetState: ModalBottomSheetState,
    onImageSelected: (Uri?) -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = modalSheetState, sheetShape = RoundedCornerShape(
            bottomEnd = 0.dp, bottomStart = 0.dp, topStart = 16.dp, topEnd = 16.dp
        ), sheetContent = {
            PhotoSelectorView(onImageSelected, modalSheetState)
        }, content = content
    )
}


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
fun PhotoSelectorView(onImageSelected: (Uri?) -> Unit, modalSheetState: ModalBottomSheetState) {

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val file = context.createImageFile()

    val uri: Uri = FileProvider.getUriForFile(
        context, context.packageName + ".provider", file
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) onImageSelected(uri)
            coroutineScope.launch {
                modalSheetState.hide()
            }
        }

    val requiredPermissionsState =
        rememberPermissionState(permission = Manifest.permission.CAMERA) { isGranted ->
            if (isGranted) {
                cameraLauncher.launch(uri)
            }
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { selectedImage ->
            onImageSelected(selectedImage)
            coroutineScope.launch {
                modalSheetState.hide()
            }
        }

    val (status, setStatus) = remember {
        mutableStateOf<PermissionStatus>(Granted)
    }

    LaunchedEffect(key1 = requiredPermissionsState.status) {
        setStatus(requiredPermissionsState.status)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.choose_image_component_title), fontSize = 20.sp
        )

        Spacer(Modifier.height(16.dp))
        if (status == Denied(true)) {
            Text(text = stringResource(id = R.string.choose_image_component_request_permission_dialog))
            Button(
                text = stringResource(id = R.string.choose_image_component_go_to_settings),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(context, intent, null)
            }
        } else {
            Row {

                Button(
                    text = stringResource(id = R.string.choose_image_component_from_gallery),
                    modifier = Modifier
                        .weight(1.0f)
                        .height(200.dp)
                        .padding(16.dp)
                ) {
                    galleryLauncher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }

                Button(
                    text = stringResource(id = R.string.choose_image_component_open_camera),
                    modifier = Modifier
                        .weight(1.0f)
                        .height(200.dp)
                        .padding(16.dp)
                ) {
                    if (requiredPermissionsState.status.isGranted.not()) requiredPermissionsState.launchPermissionRequest()
                    else cameraLauncher.launch(uri)
                }
            }
        }
    }

}

