package org.example.project.platform

import org.koin.core.component.KoinComponent

actual fun KoinComponent.checkFileSize(uri: String, maxSizeMb: Int): Boolean {
   return true
}