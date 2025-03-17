package org.example.project.screens.adaptive.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.data.repository.AiAssistantManager
import org.example.project.domain.manager.MessageObserverManager
import org.example.project.domain.repository.AiAssistantRepository
import org.example.project.platform.AudioRecorder
import org.example.project.platform.BaseScreenModel
import org.example.project.platform.Either
import org.example.project.platform.Failure
import org.example.project.platform.SpeechToTextRecognizer
import org.example.project.platform.permission.service.PermissionsService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

internal class RootViewModel : ViewModel(), KoinComponent {
    val observerManager: MessageObserverManager by inject()
    val aiManager: AiAssistantManager by inject()
//    val speechRecognizer: SpeechToTextRecognizer by inject()
    val audioRecorder: AudioRecorder by inject()
    val audioPermissionsService: PermissionsService by inject()
    private val aiRepository: AiAssistantRepository by inject()


    fun sendMessage(input: String) {
        viewModelScope.launch(handler) {
                aiRepository.sendMessage(input)
            }
    }


    protected fun handleError(failure: Throwable) {
    }

    protected val handler = CoroutineExceptionHandler { _, exception ->
        println(exception)
        if (exception.message?.contains(
                "HttpClientCall",
                true
            ) == true
        ) return@CoroutineExceptionHandler
        handleError(exception)
    }


}