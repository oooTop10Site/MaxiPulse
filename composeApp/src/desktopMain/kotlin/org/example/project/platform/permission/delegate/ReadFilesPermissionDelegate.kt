package org.example.project.platform.permission.delegate


import org.example.project.platform.permission.delegate.PermissionDelegate
import org.example.project.platform.permission.model.PermissionState

actual class ReadFilesPermissionDelegate : PermissionDelegate {


    override fun getPermissionState(): PermissionState {
       return PermissionState.GRANTED
    }

    override suspend fun providePermission() {

    }

    override fun openSettingPage() {
    }
}

private val permission: List<String> =
    listOf(
    )