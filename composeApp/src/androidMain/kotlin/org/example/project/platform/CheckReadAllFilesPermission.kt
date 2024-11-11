package org.example.project.platform

import android.os.Build
import android.os.Environment

actual fun checkReadAllFilesPermission(): Boolean? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager()
    } else {
        null
    }
}