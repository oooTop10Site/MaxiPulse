package org.example.project.platform

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.content.PartData

/**Работа с мультпартом*/
interface MultipartManager {
    suspend fun createMultipart(file: Form): MultiPartFormDataContent {
        return createMultipart(listOf(file))
    }

    suspend fun createMultipart(files: List<Form>): MultiPartFormDataContent
    suspend fun createFormData(files: List<Form>): List<PartData>
}

sealed interface Form {
    class FormFile(
        val key: String,
        val uri: String
    ) : Form

    class FormBody(
        val key: String,
        val value: String,
    ) : Form

    class FormBodyBool(
        val key: String,
        val value: Boolean
    ) : Form

    class FormBodyInt(
        val key: String,
        val value: Int
    ) : Form
}

sealed interface FormFull {

    class FormVideoFile(
        val key: String,
        val previewKey: String,
        val name: String,
        val previewName: String,
        val byteArray: ByteArray,
        val previewByteArray: ByteArray
    ) : FormFull
    
    class FormFileFull(
        val key: String,
        val name: String,
        val byteArray: ByteArray
    ) : FormFull

    class FormBodyFull(
        val key: String,
        val value: String,
    ) : FormFull

    class FromBodyFullBool(
        val key: String,
        val value: Boolean
    ) : FormFull


    class FromBodyFullInt(
        val key: String,
        val value: Int
    ) : FormFull
}
