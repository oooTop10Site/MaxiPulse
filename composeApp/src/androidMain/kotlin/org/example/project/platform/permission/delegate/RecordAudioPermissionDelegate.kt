package org.example.project.platform.permission.delegate

import android.Manifest
import android.app.Activity
import android.content.Context
import org.example.project.di.KoinInjector
import org.example.project.platform.permission.checkPermissions
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.model.PermissionState
import org.example.project.platform.permission.openAppSettingsPage
import org.example.project.platform.permission.providePermissions
import org.example.project.platform.permission.util.PermissionRequestException

actual class RecordAudioPermissionDelegate: PermissionDelegate {
    private val context by KoinInjector.koin.inject<Context>()

    private fun activity(): Activity {
        val temp: Activity by KoinInjector.koin.inject()
        return temp
    }
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity(), permission)
    }

    override suspend fun providePermission() {
        activity().providePermissions(permission) {
            throw PermissionRequestException(Permission.RECORD_AUDIO.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.RECORD_AUDIO)
    }
}

private val permission: List<String> =
    listOf(Manifest.permission.RECORD_AUDIO)