package ir.hirkancorp.presenter.core.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionComponent(
    requiredPermissions: List<String> = emptyList(),
    onPermissionGranted: () -> Unit,
    onPermissionDenied: (permissions: List<String>) -> Unit,
    onPermissionsRevoked: (permissions: List<String>) -> Unit
) {

    val permissionState = rememberMultiplePermissionsState(permissions = requiredPermissions)

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val allPermissionsGranted = permissions.isNotEmpty() && permissions.all { it.value }
            if (allPermissionsGranted) {
                onPermissionGranted()
            }
        }
    )

    LaunchedEffect(key1 = permissionState) {

        val revokedPermissions = permissionState.permissions.size == permissionState.revokedPermissions.size

        val permissionsToRequest = permissionState.permissions.filter { permission ->
            !permission.status.isGranted
        }

        when {
            permissionsToRequest.isNotEmpty() -> {
                val permissions = permissionsToRequest.map { it.permission }.toTypedArray()
                permissionLauncher.launch(permissions)
            }
        }

        if (revokedPermissions) {
            onPermissionsRevoked(permissionState.revokedPermissions.map { it.permission })
        } else {
            if (permissionState.allPermissionsGranted) {
                val permissions = permissionsToRequest.map { it.permission}
                onPermissionGranted()
            } else {
                val permissions = permissionsToRequest.map { it.permission }
                onPermissionDenied(permissions)
            }
        }

    }

}