package org.example.project.platform

expect class AudioRecorder() {
    fun startRecording()
    fun stopRecording()
    fun setOnRecordingStartedListener(listener: () -> Unit)
    fun setOnRecordingStoppedListener(listener: (String) -> Unit)
}