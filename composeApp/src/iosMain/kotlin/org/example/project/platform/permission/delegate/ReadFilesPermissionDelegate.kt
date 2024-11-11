package org.example.project.platform.permission.delegate

import org.example.project.platform.permission.model.PermissionState
import org.example.project.platform.permission.openAppSettingsPage
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class ReadFilesPermissionDelegate : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return when (val status: PHAuthorizationStatus = PHPhotoLibrary.authorizationStatus()) {
            PHAuthorizationStatusAuthorized -> PermissionState.GRANTED
            PHAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            PHAuthorizationStatusDenied -> PermissionState.DENIED
            else -> error("unknown gallery authorization status $status")
        }
    }

    override suspend fun providePermission() {
        when (PHPhotoLibrary.authorizationStatus()) {
            PHAuthorizationStatusAuthorized -> return
            PHAuthorizationStatusNotDetermined -> {
                suspendCoroutine { continuation ->
                    requestGalleryAccess { continuation.resume(it) }
                }
                providePermission()
            }

//            PHAuthorizationStatusDenied -> throw DeniedAlwaysException(Permission.GALLERY)
//            else -> error("unknown gallery authorization status $status")
        }
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }


    private fun requestGalleryAccess(callback: (PHAuthorizationStatus) -> Unit) {
        PHPhotoLibrary.requestAuthorization { status: PHAuthorizationStatus ->
            callback(status)
        }
    }
}