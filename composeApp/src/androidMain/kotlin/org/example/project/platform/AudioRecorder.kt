package org.example.project.platform

import android.content.Context
import android.media.MediaRecorder
import org.example.project.di.KoinInjector
import org.koin.core.component.KoinComponent
import java.io.File
import java.io.IOException

actual class AudioRecorder : KoinComponent {
    val context by KoinInjector.koin.inject<Context>()
    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: File? = null
    private var onRecordingStartedListener: (() -> Unit)? = null
    private var onRecordingStoppedListener: ((String) -> Unit)? = null

    actual fun startRecording() {
        val externalFilesDir = context.getExternalFilesDir(null)
        if (externalFilesDir == null) {
            println("Android: External files dir is null, falling back to cache")
            outputFile = File(context.cacheDir, "recording_${System.currentTimeMillis()}.mp3")
        } else {
            outputFile = File(externalFilesDir, "recording_${System.currentTimeMillis()}.mp3")
        }
        println("Android: Output file path: ${outputFile?.absolutePath}")

        outputFile?.parentFile?.let { parent ->
            if (!parent.exists()) {
                val created = parent.mkdirs()
                println("Android: Parent dir created: $created")
            }
        }

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.UNPROCESSED) // Без фильтров для чистого звука
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC) // или AAC_ELD, если поддерживается
            setAudioSamplingRate(48000) // Максимальная частота 48 кГц
            setAudioEncodingBitRate(320000) // Максимальный битрейт 320 кбит/с
            setAudioChannels(2) // Стерео
            setOutputFile(outputFile?.absolutePath)

            try {
                prepare()
                start()
                onRecordingStartedListener?.invoke()
                println("Android: Recording started successfully")
            } catch (e: IOException) {
                println("Android: Failed to start recording: ${e.message}")
                outputFile?.delete()
            } catch (e: IllegalStateException) {
                println("Android: Illegal state during start: ${e.message}")
                outputFile?.delete()
            }
        }
    }

    actual fun stopRecording() {
        mediaRecorder?.apply {
            try {
                stop()
                println("Android: Recording stopped, checking file...")
                outputFile?.let { file ->
                    if (file.exists() && file.length() > 0) {
                        println("Android: File exists, size: ${file.length()} bytes")
                        println("Android: Directory contents: ${file.parentFile?.listFiles()?.joinToString { it.name }}")
                        onRecordingStoppedListener?.invoke(file.absolutePath)
                    } else {
                        println("Android: File does not exist or is empty")
                        file.delete()
                    }
                }
            } catch (e: IllegalStateException) {
                println("Android: Failed to stop recording: ${e.message}")
                outputFile?.delete()
            } finally {
                release()
                mediaRecorder = null
            }
        } ?: println("Android: MediaRecorder was not initialized")
    }

    actual fun setOnRecordingStartedListener(listener: () -> Unit) {
        onRecordingStartedListener = listener
    }

    actual fun setOnRecordingStoppedListener(listener: (String) -> Unit) {
        onRecordingStoppedListener = listener
    }
}