package org.example.project.platform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import org.example.project.di.KoinInjector
import org.koin.core.component.KoinComponent
import kotlin.getValue


actual class SpeechToTextRecognizer : KoinComponent {
    val context by KoinInjector.koin.inject<Context>()
    private val speechRecognizer: SpeechRecognizer =
        SpeechRecognizer.createSpeechRecognizer(context)
    private var onResultListener: ((String) -> Unit)? = null
    private var onPartialResultListener: ((String) -> Unit)? = null

    actual fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU") // Принудительно указываем русский язык
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH) // Улучшенное распознавание
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true) // Ограничение на язык
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true) // Включаем промежуточные результаты
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ru-RU") // Предпочтительный язык
            putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, arrayOf("ru-RU")) // Разрешаем только русский
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1) // Ограничение количества результатов
        }
        speechRecognizer.startListening(intent)
    }



    actual fun stopListening() {
        speechRecognizer.stopListening()
        onResultListener = null
        onPartialResultListener = null
    }

    actual fun setOnPartialResultListener(listener: (String) -> Unit) {
        onPartialResultListener = listener
    }

    actual fun setOnResultListener(listener: (String) -> Unit) {
        onResultListener = listener
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                println("matches - $matches")
                if (!matches.isNullOrEmpty()) {
                    println("matches[0] - ${matches[0]}")
                    onResultListener?.invoke(matches[0])
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                println("onPartialResults - $partialResults")
                val matches =
                    partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                println("matchesPART - $matches")
                if (!matches.isNullOrEmpty()) {
                    onPartialResultListener?.invoke(matches[0]) // Промежуточный результат
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
            }

            override fun onReadyForSpeech(params: Bundle?) {
            }

            override fun onBeginningOfSpeech() {
            }

            override fun onRmsChanged(rmsdB: Float) {
            }

            override fun onBufferReceived(buffer: ByteArray?) {
            }

            override fun onEndOfSpeech() {
            }

            override fun onError(error: Int) {
                // Handle error
            }

            // Implement other required methods of RecognitionListener
        })
    }
}