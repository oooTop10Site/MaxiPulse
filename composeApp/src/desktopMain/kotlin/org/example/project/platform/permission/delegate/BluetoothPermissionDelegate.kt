package org.example.project.platform.permission.delegate

import org.example.project.platform.permission.model.PermissionState

actual class BluetoothPermissionDelegate: PermissionDelegate {
    override fun getPermissionState(): PermissionState {
       return PermissionState.GRANTED
    }

    override suspend fun providePermission() {

    }

    override fun openSettingPage() {

    }
}