package org.example.project.platform.permission.delegate

import org.example.project.di.KoinInjector
import org.example.project.platform.checkReadAllFilesPermission
import org.example.project.platform.permission.delegate.PermissionDelegate
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.model.PermissionState
import org.example.project.platform.permission.model.PermissionState.GRANTED
import org.example.project.platform.permission.util.PermissionRequestException

actual class ManageExternalStoragePermissionDelegate : PermissionDelegate {


    override fun getPermissionState(): PermissionState {
        return GRANTED
    }

    override suspend fun providePermission() {
        println("Я тут")
    }

    override fun openSettingPage() {

    }
}

private val permission: List<String> = listOf(

)
