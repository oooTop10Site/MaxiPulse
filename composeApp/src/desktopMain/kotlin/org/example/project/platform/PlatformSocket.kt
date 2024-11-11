package org.example.project.platform

//Desktop

internal actual class PlatformSocket actual constructor(url: String, token: String) {
    private val socketEndpoint = url
//    private var webSocket: okhttp3.WebSocket? = null
    private val authToken = token
    actual fun openSocket(listener: PlatformSocketListener) {

    }

    actual fun closeSocket(code: Int, reason: String) {

    }

    actual fun sendMessage(msg: String) {

    }
}