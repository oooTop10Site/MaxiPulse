package org.example.project.ext

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.webkit.MimeTypeMap
import org.example.project.utils.RealPathUtil
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Функция для обработки файла на основе его URI, включая копирование файла, изменение имени файла,
 * сжатие изображения и определение MIME-типа файла.
 * @param context Контекст приложения.
 * @param uri URI файла.
 * @return Обработанный файл или null, если произошла ошибка.
 */

suspend fun file(context: Context, uri: String?): File? {
    try {
        var file = if (uri?.startsWith("/storage") == true) {
            File(uri)
        } else {
            val fileUri = Uri.parse(uri)
            val path = RealPathUtil.getRealPath(context, fileUri)
            File(path)
        }
        file = if (file.name.contains(" ")) {
            val fileWithoutSpaces: File
            val newName = file.name.replace(" ", "_").replace(Regex("[()]"), "")
            fileWithoutSpaces = File(file.parent, newName)
            file.copyTo(fileWithoutSpaces) // Создаем копию файла с новым именем
        } else {
            file
        }
        println("file - ${file.extension}")
        return file

    } catch (e: Exception) {
        return null
    }
}

/**
 * проверка, является ли файл видеофайлом на основе его расширения.
 * @return true, если файл является видеофайлом, иначе false.
 */


fun File.isVideoFileByExtension(): Boolean {
    val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(this.extension)
    return mimeType?.startsWith("video/") == true
}

/**
 * Метод для извлечения превью из видео, преобразуем в .png, берем первую секунду ролика
 * @param context
 * @param videoUri  Uri видео, из которого нужно извлечь превью, видео получаем с устройства
 * @return ByteArray преобразуем для отправки на сервер
 */

fun extractVideoPreview(context: Context, videoUri: String): ByteArray {
    val retriever = MediaMetadataRetriever()
    val uri = Uri.parse(videoUri)
    retriever.setDataSource(context, uri)
    val frame = retriever.getFrameAtTime(1000)
    val byteArrayOutputStream = ByteArrayOutputStream()
    frame?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    retriever.release()
    return byteArrayOutputStream.toByteArray()
}
