package org.example.project.platform

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.example.project.di.KoinInjector
import org.koin.core.component.KoinComponent

public data class AndroidFile(
    override val path: String,
    override val platformFile: Uri,
    override val ext: String
) : MPFile<Uri>


/**
 * Компонент для выбора файла на платформе Android.
 *
 * @param show Флаг, показывающий нужно ли отображать диалог выбора файла.
 * @param initialDirectory Начальная директория для выбора файла.
 * @param fileExtensions Список расширений файлов для фильтрации.
 * @param onFileSelected Callback функция для выбора файла.
 */

@Composable
public actual fun KoinComponent.FilesPicker(
    show: Boolean,
    initialDirectory: String?,
    fileExtensions: List<String>,
    onlyImage: Boolean,
    onFileSelected: FilesSelected,
) {
    val context by KoinInjector.koin.inject<Context>()

    val launcherOnlyImage =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia()) { result ->
            if (result.isNotEmpty()) {
                onFileSelected(result.map {
                    val fileExt = context.getFileExtension(uri = it)
                    AndroidFile(
                        it.toString(),
                        it,
                        ext = fileExt.orEmpty()
                    )
                })
            } else {
                onFileSelected(null)
            }
        }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenMultipleDocuments()) { result ->
            if (result.isNotEmpty()) {
                onFileSelected(result.map {
                    val fileExt = context.getFileExtension(uri = it)
                    AndroidFile(
                        it.toString(),
                        it,
                        ext = fileExt.orEmpty()
                    )
                })
            } else {
                onFileSelected(null)
            }
        }

    val mimeTypeMap = MimeTypeMap.getSingleton()
    val mimeTypes = if (fileExtensions.isNotEmpty()) {
        fileExtensions.mapNotNull { ext ->
            mimeTypeMap.getMimeTypeFromExtension(ext)
        }.toTypedArray()
    } else {
        emptyArray()
    }

    LaunchedEffect(show) {
        if (show) {
            if (onlyImage) {
                launcherOnlyImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                launcher.launch(mimeTypes)
            }
        }
    }
}

@Composable
public actual fun ImagePicker(
    show: Boolean,
    initialDirectory: String?,
    onFileSelected: FileSelected
) {
    val context by KoinInjector.koin.inject<Context>()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { result ->
            if (result != null) {
                onFileSelected(
                    AndroidFile(
                        result.toString(),
                        result,
                        ext = context.getFileExtension(uri = result).orEmpty()
                    )
                )
            } else {
                onFileSelected(null)
            }
        }
    LaunchedEffect(show) {
        if (show) {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
}

@SuppressLint("Range")
fun Context.getFileExtension(uri: Uri): String? {
    val contentResolver = this.contentResolver
    val mimeType = contentResolver.getType(uri)

    return if (mimeType != null) {
        MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
    } else {
        val cursor = contentResolver.query(uri, null, null, null, null)
        var extension: String? = null
        cursor?.use {
            if (it.moveToFirst()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                extension = displayName.substringAfterLast('.', "")
            }
        }
        extension
    }
}