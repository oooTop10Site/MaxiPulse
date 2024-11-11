package org.example.project.platform

import org.example.project.platform.FilePickerLauncher.Mode.Directory
import org.example.project.platform.FilePickerLauncher.Mode.File
import platform.Foundation.NSURL
import platform.UIKit.UIAdaptivePresentationControllerDelegateProtocol
import platform.UIKit.UIApplication
import platform.UIKit.UIDocumentPickerDelegateProtocol
import platform.UIKit.UIDocumentPickerViewController
import platform.UIKit.UIPresentationController
import platform.UniformTypeIdentifiers.UTType
import platform.UniformTypeIdentifiers.UTTypeContent
import platform.UniformTypeIdentifiers.UTTypeFolder
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.native.concurrent.ThreadLocal

/**
 * Wraps platform specific implementation for launching a
 * File Picker.
 *
 * @param initialDirectory Initial directory that the
 *  file picker should open to.
 * @param pickerMode [Mode] to open the picker with.
 *
 */
public class FilePickerLauncher(
    private val initialDirectory: String?,
    private val pickerMode: Mode,
    private val onFileSelected: FilesSelected,
) {

    @ThreadLocal
    public companion object {
        internal var activeLauncher: FilePickerLauncher? = null
    }

    public sealed interface Mode {
        public data object Directory : Mode
        public data class File(val extensions: List<String>) : Mode
    }

    private val pickerDelegate = object : NSObject(),
        UIDocumentPickerDelegateProtocol,
        UIAdaptivePresentationControllerDelegateProtocol {

        override fun documentPicker(
            controller: UIDocumentPickerViewController, didPickDocumentsAtURLs: List<*>
        ) {
            (didPickDocumentsAtURLs.firstOrNull() as? NSURL).let { selected ->
                val ext = selected?.pathExtension ?: ""
                onFileSelected(selected?.let { listOf(IosFile(it.path ?: "", it, ext)) })
            }
        }

        override fun documentPickerWasCancelled(
            controller: UIDocumentPickerViewController
        ) {
            onFileSelected(null)
        }

        override fun presentationControllerWillDismiss(
            presentationController: UIPresentationController
        ) {
            (presentationController.presentedViewController as? UIDocumentPickerViewController)
                ?.let { documentPickerWasCancelled(it) }
        }
    }

    private val contentTypes: List<UTType>
        get() = when (pickerMode) {
            is Mode.Directory -> listOf(UTTypeFolder)
            is Mode.File -> pickerMode.extensions
                .mapNotNull { UTType.typeWithFilenameExtension(it) }
                .ifEmpty { listOf(UTTypeContent) }
        }

    private fun createPicker() = UIDocumentPickerViewController(
        forOpeningContentTypes = contentTypes
    ).apply {
        delegate = pickerDelegate
        initialDirectory?.let { directoryURL = NSURL(string = it) }
    }

    public fun launchFilePicker() {
        activeLauncher = this

        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            createPicker(),
            animated = true,
            completion = null
        )
    }
}

public suspend fun launchFilePicker(
    initialDirectory: String? = null,
    fileExtensions: List<String>,
): List<MPFile<Any>> = suspendCoroutine { cont ->
    try {
        FilePickerLauncher(
            initialDirectory = initialDirectory,
            pickerMode = FilePickerLauncher.Mode.File(fileExtensions),
            onFileSelected = { selected ->
                FilePickerLauncher.activeLauncher = null
                cont.resume(selected.orEmpty())
            }
        ).also { launcher ->
            FilePickerLauncher.activeLauncher = launcher
            launcher.launchFilePicker()
        }
    } catch (e: Throwable) {
        cont.resumeWithException(e)
    }
}

public suspend fun launchDirectoryPicker(
    initialDirectory: String? = null,
): List<MPFile<Any>> = suspendCoroutine { cont ->
    try {
        FilePickerLauncher(
            initialDirectory = initialDirectory,
            pickerMode = FilePickerLauncher.Mode.Directory,
            onFileSelected = { selected ->
                FilePickerLauncher.activeLauncher = null
                cont.resume(selected.orEmpty())
            },
        ).also { launcher ->
            FilePickerLauncher.activeLauncher = launcher
            launcher.launchFilePicker()
        }
    } catch (e: Throwable) {
        cont.resumeWithException(e)
    }
}
