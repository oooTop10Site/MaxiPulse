package org.example.project.platform

import android.content.Context
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.content.PartData
import org.example.project.ext.file
import org.example.project.ext.formData
import org.example.project.ext.multipart


class MultipartManagerImpl(private val context: Context) : MultipartManager {

    override suspend fun createMultipart(files: List<Form>): MultiPartFormDataContent {
        return multipart(files.map {
            when (it) {
                is Form.FormBody -> {
                    FormFull.FormBodyFull(it.key, it.value)
                }

                is Form.FormFile -> {
                    val file = file(context, it.uri)
                    println("file - $file")
                    FormFull.FormFileFull(
                        key = it.key,
                        name = file?.name.orEmpty(),
                        byteArray = file?.readBytes() ?: byteArrayOf()
                    )
                }

                is Form.FormBodyBool -> {
                    FormFull.FromBodyFullBool(
                        key = it.key,
                        value = it.value
                    )
                }

                is Form.FormBodyInt -> {
                    FormFull.FromBodyFullInt(
                        key = it.key,
                        value = it.value
                    )
                }

            }
        })
    }

    override suspend fun createFormData(files: List<Form>): List<PartData> {
        return formData(files.map {
            when (it) {
                is Form.FormBody -> {
                    println("NE FIlIK")
                    FormFull.FormBodyFull(it.key, it.value)
                }

                is Form.FormFile -> {
                    println("FILIK")
                    val file = file(context, it.uri)
                    FormFull.FormFileFull(
                        key = it.key,
                        name = file?.name.orEmpty(),
                        byteArray = file?.readBytes() ?: byteArrayOf()
                    )
                }

                is Form.FormBodyBool -> {
                    FormFull.FromBodyFullBool(
                        key = it.key,
                        value = it.value
                    )
                }

                is Form.FormBodyInt -> {
                    FormFull.FromBodyFullInt(
                        key = it.key,
                        value = it.value
                    )
                }
            }
        })
    }


}