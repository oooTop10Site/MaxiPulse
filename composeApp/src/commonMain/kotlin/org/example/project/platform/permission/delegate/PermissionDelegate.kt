package org.example.project.platform.permission.delegate

import org.example.project.platform.permission.model.PermissionState

internal interface PermissionDelegate {
    fun getPermissionState(): PermissionState
    suspend fun providePermission()
    fun openSettingPage()
}
