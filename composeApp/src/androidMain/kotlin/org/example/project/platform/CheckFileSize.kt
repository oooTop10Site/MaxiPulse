package org.example.project.platform

import android.content.Context
import androidx.core.net.toUri
import org.example.project.di.KoinInjector
import org.koin.core.component.KoinComponent
import java.io.InputStream

actual fun KoinComponent.checkFileSize(uri: String, maxSizeMb: Int): Boolean {
    return try {

        val context by KoinInjector.koin.inject<Context>()

        val maxSizeBytes = maxSizeMb * 1024 * 1024 // Переводим мегабайты в байты

        // Получаем InputStream для файла по его URI
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri.toUri())

        // Получаем размер файла (если InputStream не null)
        inputStream?.use {
            val fileSize = it.available().toLong()
            return fileSize <= maxSizeBytes
        }
        return false
    } catch (e: Exception) {
        return false
    }
}