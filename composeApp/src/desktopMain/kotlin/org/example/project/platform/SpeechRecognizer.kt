package org.example.project.platform

import org.koin.core.component.KoinComponent

actual class SpeechToTextRecognizer: KoinComponent {
    actual  fun startListening() {
        // Заглушка для Desktop
        println("Recording started...")
    }

    actual  fun stopListening() {
        // Заглушка для Desktop
        println("Recording stopped...")
        onResultListener?.invoke("Recognized text from desktop")
    }

    actual  fun setOnResultListener(listener: (String) -> Unit) {
        onResultListener = listener
    }

    private var onResultListener: ((String) -> Unit)? = null

    actual  fun setOnPartialResultListener(listener: (String) -> Unit) {
    }
}