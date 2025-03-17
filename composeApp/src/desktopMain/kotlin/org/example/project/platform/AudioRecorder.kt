package org.example.project.platform

import java.io.File
import javax.sound.sampled.*
import kotlin.concurrent.thread

actual class AudioRecorder {
    private var outputFile: File? = null
    private var onRecordingStartedListener: (() -> Unit)? = null
    private var onRecordingStoppedListener: ((String) -> Unit)? = null
    private var recordingThread: Thread? = null
    private var isRecording = false
    private var targetDataLine: TargetDataLine? = null

    actual fun startRecording() {
        // Базовая директория для записи
        val homeDir = System.getProperty("user.home")
        val musicDirName = if (System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) {
            // На Windows проверяем локализацию
            val musicLocalized = File(homeDir, "Music").takeIf { it.exists() } ?: File(homeDir, "Музыка")
            musicLocalized.name
        } else {
            "Music" // macOS и Linux
        }

        val recordingsDir = File(File(homeDir, musicDirName), "Recordings")
        if (!recordingsDir.exists()) {
            val created = recordingsDir.mkdirs()
            println("Desktop: Recordings directory created: $created")
        }
        outputFile = File(recordingsDir, "recording_${System.currentTimeMillis()}.wav")
        println("Desktop: Output file path: ${outputFile?.absolutePath}")

        val preferredFormat = AudioFormat(
            44100f, // Частота дискретизации
            16,     // Разрядность
            2,      // Каналы (стерео)
            true,   // Signed
            false   // Little-endian
        )

        val info = DataLine.Info(TargetDataLine::class.java, preferredFormat)
        if (!AudioSystem.isLineSupported(info)) {
            println("Desktop: Preferred format not supported: $preferredFormat")
            val fallbackFormat = findSupportedFormat()
            if (fallbackFormat == null) {
                println("Desktop: No supported audio format found")
                return
            }
            println("Desktop: Using fallback format: $fallbackFormat")
            startRecordingWithFormat(fallbackFormat)
        } else {
            startRecordingWithFormat(preferredFormat)
        }
    }

    private fun findSupportedFormat(): AudioFormat? {
        val sampleRates = floatArrayOf(48000f, 44100f, 32000f, 16000f, 8000f)
        val channels = intArrayOf(2, 1)
        val sampleSizes = intArrayOf(16, 8)

        for (rate in sampleRates) {
            for (channel in channels) {
                for (size in sampleSizes) {
                    val format = AudioFormat(rate, size, channel, true, false)
                    val info = DataLine.Info(TargetDataLine::class.java, format)
                    if (AudioSystem.isLineSupported(info)) {
                        return format
                    }
                }
            }
        }
        return null
    }

    private fun startRecordingWithFormat(format: AudioFormat) {
        targetDataLine = AudioSystem.getTargetDataLine(format).apply {
            open(format)
            start()
        }

        isRecording = true
        recordingThread = thread {
            val audioInputStream = AudioInputStream(targetDataLine)
            try {
                onRecordingStartedListener?.invoke()
                println("Desktop: Recording started with format: $format")
                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile)
            } catch (e: Exception) {
                println("Desktop: Failed to start recording: ${e.message}")
            }
        }
    }

    actual fun stopRecording() {
        isRecording = false
        targetDataLine?.stop()
        targetDataLine?.close()
        recordingThread?.join()
        outputFile?.absolutePath?.let { path ->
            if (outputFile?.exists() == true && outputFile?.length() ?: 0 > 0) {
                onRecordingStoppedListener?.invoke(path)
                println("Desktop: Recording stopped, file saved at: $path, size: ${outputFile?.length()} bytes")
            } else {
                println("Desktop: File not saved or empty")
            }
        }
        targetDataLine = null
        recordingThread = null
    }

    actual fun setOnRecordingStartedListener(listener: () -> Unit) {
        onRecordingStartedListener = listener
    }

    actual fun setOnRecordingStoppedListener(listener: (String) -> Unit) {
        onRecordingStoppedListener = listener
    }
}