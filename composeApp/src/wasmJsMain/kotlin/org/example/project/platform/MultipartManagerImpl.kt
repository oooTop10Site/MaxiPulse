package org.example.project.platform

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.content.PartData
import org.example.project.ext.multipart


class MultipartManagerImpl() : MultipartManager {

    override suspend fun createMultipart(files: List<Form>): MultiPartFormDataContent {
        return multipart(files.map {
            when (it) {
                is Form.FormBody -> {
                    FormFull.FormBodyFull(it.key, it.value)
                }

                is Form.FormFile -> {
                    FormFull.FormFileFull(
                        key = it.key,
                        name = "error",
                        byteArray = byteArrayOf()
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
        return emptyList()
    }


}