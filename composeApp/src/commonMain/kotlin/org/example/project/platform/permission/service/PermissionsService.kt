package org.example.project.platform.permission.service

import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.model.PermissionState
import kotlinx.coroutines.flow.Flow

interface PermissionsService {
    fun checkPermission(permission: Permission): PermissionState
    fun checkPermissionFlow(permission: Permission): Flow<PermissionState>
    suspend fun providePermission(permission: Permission)
    fun openSettingPage(permission: Permission)

    companion object {
        const val PERMISSION_CHECK_FLOW_FREQUENCY = 1000L
    }
}
