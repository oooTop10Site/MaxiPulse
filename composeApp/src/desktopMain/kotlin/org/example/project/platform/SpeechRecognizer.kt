package org.example.project.platform

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.vosk.Model
import org.vosk.Recognizer
import javax.sound.sampled.*
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.TargetDataLine

actual class SpeechToTextRecognizer {
    private var recognizer: Recognizer? = null
    private var isListening = false
    private var onResultListener: ((String) -> Unit)? = null
    private var onPartialResultListener: ((String) -> Unit)? = null
    private var microphone: TargetDataLine? = null
    private var job: Job? = null

    actual fun startListening() {
        println("LOG - STARTING")

        try {
            if (recognizer == null && microphone == null && job == null) {
                stopListening() // Останавливаем, если уже работаем
            }

            val model = Model("models/vosk-model-small-ru-0.22")
            recognizer = Recognizer(model, 44100.0f)

            isListening = true
            job = CoroutineScope(Dispatchers.Default).launch {
                try {
                    val format = AudioFormat(44100.0f, 16, 1, true, false)
                    val line = getAvailableMicrophone(format)
                    microphone = line
                    line.open(format)
                    line.start()

                    val buffer = ByteArray(4096)
                    while (isListening) {
                        val numBytesRead = line.read(buffer, 0, buffer.size)
                        if (recognizer?.acceptWaveForm(buffer, numBytesRead) == true) {
//                            val result = recognizer?.result ?: ""
//                            onPartialResultListener?.invoke(result)
                        } else {
                            val partial = recognizer?.partialResult
                                ?.substringAfter(": \"", "")
                                ?.substringBefore("\"", "") ?: ""
                            println("partial - $partial")
                            if(partial.isNotBlank()) {
                                onPartialResultListener?.invoke(partial)
                            }
                        }
                    }
                } catch (e: Exception) {
                    println("Ошибка в корутине: ${e.message}")
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            println("Ошибка: ${e.message}")
            e.printStackTrace()
            stopListening()
        }
    }

    actual fun stopListening() {
        println("LOG - STOPING")
        if (!isListening) return // Уже остановлено

        isListening = false

        job?.cancel()
        job = null

        // Очищаем recognizer **до** закрытия микрофона, чтобы избежать краша
        recognizer?.let {
            try {
                val finalResult = it.finalResult?.substringAfter(": \"", "")
                    ?.substringBefore("\"", "") ?: ""
                onResultListener?.invoke(finalResult)
            } catch (e: Exception) {
                println("Ошибка получения finalResult: ${e.message}")
            } finally {
                try {
                    it.close() // Закрываем Vosk
                } catch (e: Exception) {
                    println("Ошибка при закрытии recognizer: ${e.message}")
                }
            }
        }
        recognizer = null // Убираем ссылку

        microphone?.let {
            try {
                it.stop()
                it.flush()
                it.close()
            } catch (e: Exception) {
                println("Ошибка при закрытии микрофона: ${e.message}")
            }
        }
        microphone = null
    }

    actual fun setOnPartialResultListener(listener: (String) -> Unit) {
        onPartialResultListener = listener
    }

    actual fun setOnResultListener(listener: (String) -> Unit) {
        onResultListener = listener
    }

    private fun getAvailableMicrophone(format: AudioFormat): TargetDataLine {
        val mixers = AudioSystem.getMixerInfo()
        for (mixerInfo in mixers) {
            val mixer = AudioSystem.getMixer(mixerInfo)
            val targetLines = mixer.targetLineInfo
            for (lineInfo in targetLines) {
                try {
                    val line = mixer.getLine(lineInfo) as TargetDataLine
                    line.open(format) // Пробуем открыть линию
                    return line
                } catch (e: Exception) {
                    // Игнорируем ошибки
                }
            }
        }
        throw IllegalStateException("Не найдено доступных микрофонов")
    }
}
