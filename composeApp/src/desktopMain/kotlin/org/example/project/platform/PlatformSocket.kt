package org.example.project.platform

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

//Desktop

internal actual class PlatformSocket actual constructor(url: String, token: String) {
    private val socketEndpoint = url
    private var webSocket: WebSocket? = null
    private val authToken = token
    actual fun openSocket(listener: PlatformSocketListener) {
        val trustAllCertificates = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCertificates, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory
        val socketRequest = Request.Builder()
            .url(socketEndpoint)
            .addHeader("Authorization", "Bearer $authToken") // Добавляем заголовок с токеном
            .build()
        val webClient = OkHttpClient().newBuilder()
            .sslSocketFactory(sslSocketFactory, trustAllCertificates[0] as X509TrustManager)
            .retryOnConnectionFailure(true)
            .build()
        webSocket = webClient.newWebSocket(
            socketRequest,
            object : okhttp3.WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) = listener.onOpen()
                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) =
                    listener.onFailure(t)

                override fun onMessage(webSocket: WebSocket, text: String) =
                    listener.onMessage(text)

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) =
                    listener.onClosing(code, reason)

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) =
                    listener.onClosed(code, reason)
            }
        )
    }

    actual fun closeSocket(code: Int, reason: String) {
        try {
            webSocket?.close(code, reason)
            webSocket = null
        } catch (e: Exception) {
            println(reason)
        }
    }

    actual fun sendMessage(msg: String) {
        try {
            println("webclient - $webSocket")
            println("message - $msg")
            webSocket?.send(msg)
        } catch (e: Exception) {
            println(e.message)
            println(e.message)
            println(e.message)
            println(e.message)
            println(e.message)
            println(e.message)
        }
    }
}