package org.example.project.platform.permission

import org.example.project.platform.permission.delegate.BluetoothPermissionDelegate
import org.example.project.platform.permission.delegate.PermissionDelegate
import org.example.project.platform.permission.delegate.ReadFilesPermissionDelegate
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.service.PermissionsService
import org.example.project.platform.permission.service.PermissionsServiceImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.example.project.platform.permission.delegate.ManageExternalStoragePermissionDelegate
import org.example.project.platform.permission.delegate.RecordAudioPermissionDelegate


val platformPermissionsModule: Module = module {
    single<PermissionDelegate>(named(Permission.READ_EXTERNAL_STORAGE.name)) { ReadFilesPermissionDelegate() }
    single<PermissionDelegate>(named(Permission.MANAGE_EXTERNAL_STORAGE.name)) { ManageExternalStoragePermissionDelegate() }
    single<PermissionDelegate>(named(Permission.BLUETOOTH_CONNECT.name)) { BluetoothPermissionDelegate() }
    single<PermissionDelegate>(named(Permission.RECORD_AUDIO.name)) { RecordAudioPermissionDelegate() }
    single<PermissionsService> {
        PermissionsServiceImpl()
    }
}
