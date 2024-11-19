package org.example.project.platform

import org.example.project.domain.manager.MessageObserverManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class Failure(override val message: String) : Throwable(), KoinComponent {

    internal val observerManager: MessageObserverManager by inject()
    init {
        println("observerManager 2 - $observerManager")
    }

    class Message(message: String) : Failure(message) {
        init {
            println("ошибочка отправить $message")
            observerManager.putMessage(message)
        }
    }

    class LimitedFileSize(message: String) : Failure(message) {
        init {
            println("ошибочка отправить $message")
            observerManager.putMessage(message)
        }
    }

    class HardMessage(message: String) : Failure(message) {
        init {
            println("ошибочка отправить $message")
            observerManager.putMessage(message)
        }
    }

    class Http(val code: Int, override val message: String) : Failure(message) {
        init {
            println("ошибочка отправить $message")
            observerManager.putMessage(message)
        }
    }

    class UnAuth(val code: Int, override val message: String) : Failure(message) {
        init {
            println("ошибочка отправить $message")
            observerManager.putMessage(message)
        }
    }
}