package org.example.project.platform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import org.koin.core.component.KoinComponent
import platform.Foundation.NSURL

public data class IosFile(
	override val path: String,
	override val platformFile: NSURL,
	override val ext: String
) : MPFile<NSURL>

@Composable
public actual fun KoinComponent.FilesPicker(
	show: Boolean,
	initialDirectory: String?,
	fileExtensions: List<String>,
	onlyImage: Boolean,
	onFileSelected: FilesSelected
) {
	val launcher = remember {
		FilePickerLauncher(
			initialDirectory = initialDirectory,
			pickerMode = FilePickerLauncher.Mode.File(fileExtensions),
			onFileSelected = onFileSelected,
		)
	}

	LaunchedEffect(show) {
		if (show) {
			launcher.launchFilePicker()
		}
	}
}

@Composable
public actual fun ImagePicker(
	show: Boolean,
	initialDirectory: String?,
	onFileSelected: FileSelected
) {
	// Поддерживаемые расширения для изображений
	val imageExtensions = listOf("png", "jpg", "jpeg")

	// Создаем и запоминаем экземпляр FilePickerLauncher
	val launcher = remember {
		FilePickerLauncher(
			initialDirectory = initialDirectory,
			pickerMode = FilePickerLauncher.Mode.File(imageExtensions),
			onFileSelected = { selectedFiles ->
				// Берем первый выбранный файл, если он есть
				onFileSelected(selectedFiles?.firstOrNull())
			}
		)
	}

	LaunchedEffect(show) {
		if (show) {
			launcher.launchFilePicker()
		}
	}
}

