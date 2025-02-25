package org.example.project.platform

internal expect class SpeechToTextRecognizer() {
    fun startListening()
    fun stopListening()
    fun setOnResultListener(listener: (String) -> Unit)
    fun setOnPartialResultListener(listener: (String) -> Unit)
}