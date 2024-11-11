package org.example.project.platform

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.content.PartData
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import org.example.project.ext.formData
import org.example.project.ext.multipart
import platform.CoreGraphics.CGFloat
import platform.Foundation.*
import platform.posix.memcpy
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation

class MultipartManagerImpl : MultipartManager {


    override suspend fun createMultipart(files: List<Form>): MultiPartFormDataContent {
        return multipart(files.map {
            when (it) {
                is Form.FormBody -> {
                    FormFull.FormBodyFull(it.key, it.value)
                }

                is Form.FormFile -> {
                    val filePath = getCacheDir() + it.uri
                    val compressionQuality: CGFloat = 0.8
                    val bytes = if(isImageFile(filePath)) {
                        compressImage(filePath, compressionQuality )?.toByteArray()
                    } else {
                        NSData.dataWithContentsOfFile(filePath)?.toByteArray()
                    }
                    FormFull.FormFileFull(
                        key = it.key,
                        name = it.uri,
                        byteArray = bytes ?: byteArrayOf()
                    )
                }

                is Form.FromBodyBool -> TODO()
                is Form.FromBodyInt -> TODO()
            }
        })
    }

    override suspend fun createFormData(files: List<Form>): List<PartData> {
        return formData(files.map {
            when (it) {
                is Form.FormBody -> {
                    FormFull.FormBodyFull(it.key, it.value)
                }

                is Form.FormFile -> {
                    val filePath = getCacheDir() + it.uri
                    val compressionQuality: CGFloat = 0.8
                    val bytes = if(isImageFile(filePath)) {
                        compressImage(filePath, compressionQuality )?.toByteArray()
                    } else {
                        NSData.dataWithContentsOfFile(filePath)?.toByteArray()
                    }
                    FormFull.FormFileFull(
                        key = it.key,
                        name = it.uri,
                        byteArray = bytes ?: byteArrayOf()
                    )
                }

                is Form.FromBodyBool -> TODO()
                is Form.FromBodyInt -> TODO()
            }
        })
    }


    @OptIn(ExperimentalForeignApi::class)
    fun NSData.toByteArray(): ByteArray = ByteArray(this@toByteArray.length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    fun ByteArray.toNSData(): NSData = memScoped {
        NSData.create(
            bytes = allocArrayOf(this@toNSData),
            length = this@toNSData.size.toULong()
        )
    }

    fun getCacheDir(): String {
        return (NSFileManager.defaultManager().URLsForDirectory(9.toULong(), 1.toULong())
            .firstOrNull() as? NSURL)?.path + "/"
    }

    fun isImageFile(filePath: String): Boolean {
        val imageExtensions = listOf(".jpg", ".jpeg", ".png", ".gif", ".bmp")
        val fileExtension = filePath.substringAfterLast(".", "")
        return fileExtension.lowercase() in imageExtensions
    }

    fun compressImage(filePath: String, compressionQuality: CGFloat): NSData? {
        val image = UIImage.imageWithContentsOfFile(filePath)
        return image?.let { UIImageJPEGRepresentation(it, compressionQuality) }
    }
}
