package org.example.project.platform

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class Failure(override val message: String) : Throwable(), KoinComponent {


    class Message(message: String) : Failure(message) {

    }

    class LimitedFileSize(message: String) : Failure(message) {

    }

    class HardMessage(message: String) : Failure(message)

    class Http(val code: Int, override val message: String) : Failure(message) {

    }

    class UnAuth(val code: Int, override val message: String): Failure(message) {

    }
}