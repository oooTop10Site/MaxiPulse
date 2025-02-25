package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.serialization.EncodeDefault.Mode
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.component.KoinComponent


@Composable
public actual fun KoinComponent.FilesPicker(
    show: Boolean,
    initialDirectory: String?,
    fileExtensions: List<String>,
    onlyImage: Boolean,
    onFileSelected: FilesSelected
) {

}

@Composable
public actual fun ImagePicker(
    show: Boolean,
    initialDirectory: String?,
    onFileSelected: FileSelected
) {

}

class FilePickerLauncher @OptIn(ExperimentalSerializationApi::class) constructor(
    private val initialDirectory: String?,
    private val pickerMode: Mode,
    private val onFileSelected: (List<String>) -> Unit
) {
}

