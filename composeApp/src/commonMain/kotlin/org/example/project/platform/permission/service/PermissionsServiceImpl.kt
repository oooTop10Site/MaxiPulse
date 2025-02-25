package org.example.project.platform.permission.service

import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.model.PermissionState
import org.example.project.platform.permission.service.PermissionsService.Companion.PERMISSION_CHECK_FLOW_FREQUENCY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.platform.permission.delegate.PermissionDelegate
import org.example.project.platform.permission.util.getPermissionDelegate
import org.koin.core.component.KoinComponent

internal class PermissionsServiceImpl : PermissionsService, KoinComponent, JavaSerializable {
    override fun checkPermission(permission: Permission): PermissionState {
        return try {
            return getPermissionDelegate(permission).getPermissionState()
        } catch (e: Exception) {
            println("Failed to check permission $permission")
            e.printStackTrace()
            PermissionState.NOT_DETERMINED
        }
    }

    override fun checkPermissionFlow(permission: Permission): Flow<PermissionState> {
        return flow {
            while (true) {
                val permissionState = checkPermission(permission)
                emit(permissionState)
                delay(PERMISSION_CHECK_FLOW_FREQUENCY)
            }
        }
    }

    override suspend fun providePermission(permission: Permission) {
        try {
            getPermissionDelegate(permission).providePermission()
        } catch (e: Exception) {
            println("Failed to request permission $permission")
            e.printStackTrace()
        }
    }

    override fun openSettingPage(permission: Permission) {
        println("Open settings for permission $permission")
        try {
            getPermissionDelegate(permission).openSettingPage()
        } catch (e: Exception) {
            println("Failed to open settings for permission $permission")
            e.printStackTrace()
        }
    }
}
