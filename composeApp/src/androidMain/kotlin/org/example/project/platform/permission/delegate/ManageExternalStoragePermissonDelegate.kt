package org.example.project.platform.permission.delegate

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import android.provider.Settings
import org.example.project.di.KoinInjector
import org.example.project.platform.checkReadAllFilesPermission
import org.example.project.platform.permission.checkPermissions
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.model.PermissionState
import org.example.project.platform.permission.openActionSettingsPage
import org.example.project.platform.permission.providePermissions
import org.example.project.platform.permission.util.PermissionRequestException

actual class ManageExternalStoragePermissionDelegate : PermissionDelegate {

    private val context by KoinInjector.koin.inject<Context>()

    private fun activity(): Activity {
        val temp: Activity by KoinInjector.koin.inject()
        return temp
    }

    override fun getPermissionState(): PermissionState {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (checkReadAllFilesPermission() == true) PermissionState.GRANTED else PermissionState.DENIED
        } else checkPermissions(context, activity(), permission)
    }

    override suspend fun providePermission() {
        println("Я тут")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            println("и тут")

            openSettingPage()
        } else {
            println("или тут")
            activity().providePermissions(permission) {
                throw PermissionRequestException(Permission.MANAGE_EXTERNAL_STORAGE.name)
            }
        }
    }

    override fun openSettingPage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.openActionSettingsPage(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        }
    }
}

private val permission: List<String> = listOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)
