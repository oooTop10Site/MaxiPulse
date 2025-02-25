package org.example.project.platform

import androidx.compose.runtime.Composable
import org.koin.core.component.KoinComponent

public interface MPFile<out T : Any> {
    // on JS this will be a file name, on other platforms it will be a file path
    public val path: String
    public val ext: String
    public val platformFile: T
}

public typealias FilesSelected = (List<MPFile<Any>>?) -> Unit
public typealias FileSelected = (MPFile<Any>?) -> Unit

@Composable
public expect fun KoinComponent.FilesPicker(
    show: Boolean,
    initialDirectory: String? = null,
    fileExtensions: List<String>,
    onlyImage: Boolean = false,
    onFileSelected: FilesSelected
)

@Composable
public expect fun ImagePicker(
    show: Boolean,
    initialDirectory: String? = null,
    onFileSelected: FileSelected
)